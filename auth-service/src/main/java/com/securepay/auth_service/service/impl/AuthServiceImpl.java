package com.securepay.auth_service.service.impl;

import com.securepay.auth_service.dto.*;
import com.securepay.auth_service.entity.*;
import com.securepay.auth_service.repository.*;
import com.securepay.auth_service.security.JwtService;
import com.securepay.auth_service.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_MINUTES = 15;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${securepay.jwt.refresh-token-days:7}")
    private long refreshTokenDays;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    @Transactional
    public void register(UserRegisterRequest request) {

        String email = normalizeEmail(request.getEmail());
        String username = request.getUsername().trim();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Unable to register with the supplied details");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Unable to register with the supplied details");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new IllegalStateException(
                        "Default CUSTOMER role is not configured"));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(customerRole);
        user.setStatus(UserStatus.PENDING_VERIFICATION);
        user.setEmailVerified(false);
        user.setMfaEnabled(false);
        user.setFailedLoginAttempts(0);
        user.setPasswordChangedAt(Instant.now());

        userRepository.save(user);

        // TODO: Generate and persist a hashed, expiring email
        // verification token; publish a notification event.
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        String email = normalizeEmail(request.getEmail());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password"));

        Instant now = Instant.now();

        if (user.getLockedUntil() != null
                && user.getLockedUntil().isAfter(now)) {
            throw new IllegalStateException(
                    "Account temporarily locked");
        }

        if (user.getLockedUntil() != null
                && !user.getLockedUntil().isAfter(now)) {
            user.setLockedUntil(null);
            user.setFailedLoginAttempts(0);

            if (user.getStatus() == UserStatus.LOCKED) {
                user.setStatus(UserStatus.ACTIVE);
            }
        }

        if (user.getStatus() != UserStatus.ACTIVE
                || !user.isEmailVerified()) {
            throw new IllegalStateException(
                    "Account is not active or verified");
        }

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {

            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setLockedUntil(
                        now.plusSeconds(LOCK_MINUTES * 60));
                user.setStatus(UserStatus.LOCKED);
            }

            userRepository.save(user);

            throw new IllegalArgumentException(
                    "Invalid email or password");
        }

        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(now);
        userRepository.save(user);

        // TODO: If MFA is enabled, issue a challenge instead
        // of issuing tokens before MFA has been verified.

        return issueTokens(user, UUID.randomUUID());
    }

    @Override
    @Transactional
    public LoginResponse refresh(RefreshRequest request) {

        String rawToken = request.refreshToken();
        String hash = sha256(rawToken);

        RefreshToken stored = refreshTokenRepository
                .findByTokenHash(hash)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid refresh token"));

        if (stored.isRevoked()) {
            // Token reuse detection: revoke its entire family.
            UUID familyId = stored.getFamilyId();

            if (familyId != null) {
                refreshTokenRepository.findAll().stream()
                        .filter(t -> familyId.equals(t.getFamilyId()))
                        .forEach(t -> t.setRevoked(true));
                refreshTokenRepository.flush();
            }

            throw new IllegalArgumentException(
                    "Invalid refresh token");
        }

        if (!stored.getExpiresAt().isAfter(Instant.now())) {
            stored.setRevoked(true);
            throw new IllegalArgumentException(
                    "Refresh token expired");
        }

        User user = stored.getUser();

        if (user.getStatus() != UserStatus.ACTIVE
                || !user.isEmailVerified()) {
            stored.setRevoked(true);
            throw new IllegalStateException(
                    "Account is not active");
        }

        // Rotate: the old refresh token cannot be used again.
        stored.setRevoked(true);

        UUID familyId = stored.getFamilyId() != null
                ? stored.getFamilyId()
                : UUID.randomUUID();

        return issueTokens(user, familyId);
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {

        refreshTokenRepository.findByTokenHash(
                sha256(refreshToken)).ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    private LoginResponse issueTokens(User user, UUID familyId) {

        String accessToken = jwtService.generateToken(user);
        String refreshToken = generateOpaqueToken();

        RefreshToken stored = new RefreshToken();
        stored.setUser(user);
        stored.setTokenHash(sha256(refreshToken));
        stored.setFamilyId(familyId);
        stored.setExpiresAt(
                Instant.now().plusSeconds(refreshTokenDays * 86400));
        stored.setRevoked(false);

        refreshTokenRepository.save(stored);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenSeconds(),
                user.getId(),
                user.getUsername(),
                user.getRole().getName()
        );
    }

    private String generateOpaqueToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String sha256(String value) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Unable to hash token", ex);
        }
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(java.util.Locale.ROOT);
    }

    @Override
    @Transactional
    public void updateRole(UpdateRoleRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Role not found"));

        user.setRole(role);
        userRepository.save(user);

        // TODO: Publish a security audit event.
    }

    @Override
    @Transactional
    public void createRole(Role request) {

        String name = request.getName().trim()
                .toUpperCase(java.util.Locale.ROOT);

        if (roleRepository.existsByName(name)) {
            throw new IllegalArgumentException(
                    "Role already exists");
        }

        Role role = new Role();
        role.setName(name);

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void changePassword(
            String email,
            ChangePasswordRequest request) {

        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid request"));

        if (!passwordEncoder.matches(
                request.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException(
                    "Invalid current password");
        }

        if (passwordEncoder.matches(
                request.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException(
                    "New password must differ from current password");
        }

        user.setPassword(passwordEncoder.encode(
                request.newPassword()));
        user.setPasswordChangedAt(Instant.now());

        // Revoke all existing refresh sessions.
        refreshTokenRepository
                .findAllByUserIdAndRevokedFalse(user.getId())
                .forEach(token -> token.setRevoked(true));

        userRepository.save(user);
    }

    @Override
    public UUID getCurrentUserId(String email) {
        return userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"))
                .getId();
    }

    @Override
    public void verifyEmail(String token) {
        throw new UnsupportedOperationException(
                "Implement with a persisted EmailVerificationToken");
    }

    @Override
    public void forgotPassword(String email) {
        // Implement a non-enumerating response, hashed reset token,
        // expiration, and notification delivery.
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        throw new UnsupportedOperationException(
                "Implement with a persisted PasswordResetToken");
    }
}
