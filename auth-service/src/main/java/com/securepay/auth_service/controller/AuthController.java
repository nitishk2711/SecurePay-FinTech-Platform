```java
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
    public ResponseEntity<ApiResponseDto<Map<String, String>>> register(
            @Valid @RequestBody UserRegisterRequest request) {
        try {
            authService.register(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Registration received. Verify your email to activate the account.",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            null
                    )
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Login failed",
                            null
                    )
            );
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDto<LoginResponse>> refresh(
            @Valid @RequestBody RefreshRequest request) {
        try {
            LoginResponse response = authService.refresh(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Invalid or expired refresh token",
                            null
                    )
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> logout(
            @Valid @RequestBody RefreshRequest request) {
        try {
            authService.logout(request.refreshToken());

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Logged out",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Logout failed",
                            null
                    )
            );
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        try {
            authService.changePassword(
                    authentication.getName(),
                    request
            );

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Password changed",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "Password change failed",
                            null
                    )
            );
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<Map<String, UUID>>> me(
            Authentication authentication) {
        try {
            UUID userId = authService.getCurrentUserId(
                    authentication.getName()
            );

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            Map.of("userId", userId)
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Unauthorized",
                            null
                    )
            );
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> verifyEmail(
            @RequestParam String token) {
        try {
            authService.verifyEmail(token);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Email verified",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "Email verification failed",
                            null
                    )
            );
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> forgotPassword(
            @RequestParam String email) {
        try {
            authService.forgotPassword(email);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "If an account exists, reset instructions will be sent.",
                            null
                    )
            );

        } catch (Exception e) {
            // Avoid revealing whether an email exists.
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "If an account exists, reset instructions will be sent.",
                            null
                    )
            );
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            authService.resetPassword(token, newPassword);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Password reset",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "Password reset failed",
                            null
                    )
            );
        }
    }
}
```
