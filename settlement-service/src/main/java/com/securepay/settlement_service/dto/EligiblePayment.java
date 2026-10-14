
package com.securepay.settlement_service.dto;

import java.math.BigDecimal;

public record EligiblePayment(
    String paymentId,
    BigDecimal amount,
    BigDecimal feeAmount,
    String currency
) {}
