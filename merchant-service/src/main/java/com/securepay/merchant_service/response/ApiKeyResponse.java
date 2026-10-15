package com.securepay.merchant_service.response;

import com.securepay.merchant_service.enums.ApiKeyStatus;
import com.securepay.merchant_service.enums.Environment;

import java.time.Instant;

public record ApiKeyResponse(
        String id,
        String apiKey,
        Environment environment,
        ApiKeyStatus status,
        Instant expiresAt,
        Instant createdAt
) {}
