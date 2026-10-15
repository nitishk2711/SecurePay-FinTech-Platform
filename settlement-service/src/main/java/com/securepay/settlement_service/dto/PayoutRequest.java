
package com.securepay.settlement_service.dto;

import java.math.BigDecimal;

public record PayoutRequest(
    String payoutId,
    String settlementId,
    String merchantId,
    BigDecimal amount,
    String currency,
    String idempotencyKey
) {}
