
package com.securepay.paymentService.dto;

public record ProcessorResponse(
        String transactionId,
        String status,
        String failureCode,
        String message
) {
}
