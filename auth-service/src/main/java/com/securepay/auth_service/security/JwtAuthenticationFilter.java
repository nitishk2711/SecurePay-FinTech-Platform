package com.securepay.auth_service.security;

import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            UUID userId = jwtService.extractUserId(token);

            User user = userRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new);

            boolean locked = user.getLockedUntil() != null
                    && user.getLockedUntil().isAfter(
                            java.time.Instant.now());

            if (user.getStatus()
                    != com.securepay.auth_service.entity.UserStatus.ACTIVE
                    || !user.isEmailVerified()
                    || locked) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Account is not available");
                return;
            }

            List<SimpleGrantedAuthority> authorities =
                    new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(
                    "ROLE_" + user.getRole().getName()));

            user.getRole().getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(
                            permission.getName())));

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            authorities);

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired access token");
            return;
        }

        chain.doFilter(request, response);
    }
}
