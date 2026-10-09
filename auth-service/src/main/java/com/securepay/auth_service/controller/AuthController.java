package com.securepay.auth_service.controller;

import com.securepay.auth_service.dto.*;
import com.securepay.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserRegisterRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message",
                        "Registration received. Verify your email to activate the account."
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @Valid @RequestBody RefreshRequest request) {

        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @Valid @RequestBody RefreshRequest request) {

        authService.logout(request.refreshToken());

        return ResponseEntity.ok(
                Map.of("message", "Logged out"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {

        authService.changePassword(
                authentication.getName(), request);

        return ResponseEntity.ok(
                Map.of("message", "Password changed"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        UUID userId = authService.getCurrentUserId(
                authentication.getName());

        return ResponseEntity.ok(
                Map.of("userId", userId));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(
            @RequestParam String token) {

        authService.verifyEmail(token);

        return ResponseEntity.ok(
                Map.of("message", "Email verified"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam String email) {

        authService.forgotPassword(email);

        return ResponseEntity.ok(
                Map.of("message",
                        "If an account exists, reset instructions will be sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {

        authService.resetPassword(token, newPassword);

        return ResponseEntity.ok(
                Map.of("message", "Password reset"));
    }
}
