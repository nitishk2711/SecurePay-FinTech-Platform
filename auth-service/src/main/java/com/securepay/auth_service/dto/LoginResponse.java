package com.securepay.auth_service.dto;

import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        UUID userId,
        String username,
        String role
) {
}
