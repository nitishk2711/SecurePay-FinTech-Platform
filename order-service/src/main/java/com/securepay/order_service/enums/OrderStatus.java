package com.securepay.order_service.enums;

public enum OrderStatus {
    CREATED,
    PENDING,
    PROCESSING,
    PAID,
    FAILED,
    CANCELLED,
    EXPIRED,
    REFUND_PENDING,
    PARTIALLY_REFUNDED,
    REFUNDED
}
