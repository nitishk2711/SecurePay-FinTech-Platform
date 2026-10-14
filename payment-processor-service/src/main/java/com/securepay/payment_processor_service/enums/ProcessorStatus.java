
package com.securepay.payment_processor_service.enums;

public enum ProcessorStatus {
    CREATED,
    PROCESSING,
    AUTHORIZED,
    CAPTURED,
    DECLINED,
    FAILED,
    PENDING,
    REQUIRES_ACTION,
    VOIDED,
    REFUNDED,
    PARTIALLY_REFUNDED
}
