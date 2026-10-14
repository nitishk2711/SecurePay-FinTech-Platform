
package com.securepay.paymentService.dto;

import java.math.BigDecimal;

public record ProcessorRequest(
        String paymentId,
        BigDecimal amount,
        String currency,
        String method
) {
}
