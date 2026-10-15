package com.securepay.transaction_service.enums;

public enum TransactionStatus {
    CREATED,
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    CANCELLED,
    REVERSED,
    REFUND_PENDING,
    PARTIALLY_REFUNDED,
    REFUNDED
}
