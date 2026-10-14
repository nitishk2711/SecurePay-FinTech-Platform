
package com.securepay.settlement_service.dto;

public record PayoutResponse(
    String payoutId,
    String status,
    String bankReference,
    String failureReason
) {}
