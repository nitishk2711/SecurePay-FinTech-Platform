package com.securepay.transaction_service.dto;

import com.securepay.transaction_service.enums.TransactionStatus;
import com.securepay.transaction_service.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        String transactionId,
        String paymentId,
        String orderId,
        BigDecimal amount,
        String currency,
        TransactionType transactionType,
        TransactionStatus status,
        BigDecimal refundedAmount,
        Instant createdAt,
        Instant completedAt
) {}
