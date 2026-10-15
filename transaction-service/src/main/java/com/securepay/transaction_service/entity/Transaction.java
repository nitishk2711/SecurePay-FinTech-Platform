package com.securepay.transaction_service.entity;

import com.securepay.transaction_service.enums.TransactionStatus;
import com.securepay.transaction_service.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "transactions",
    indexes = {
        @Index(name = "idx_txn_payment", columnList = "payment_id"),
        @Index(name = "idx_txn_order", columnList = "order_id"),
        @Index(name = "idx_txn_customer", columnList = "customer_id"),
        @Index(name = "idx_txn_merchant", columnList = "merchant_id"),
        @Index(name = "idx_txn_created", columnList = "created_at")
    }
)
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_id", nullable = false,
            unique = true, updatable = false)
    private String transactionId;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "parent_transaction_id")
    private String parentTransactionId;

    @Column(name = "processor_transaction_id")
    private String processorTransactionId;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "failure_code")
    private String failureCode;

    @Column(name = "failure_message", length = 500)
    private String failureMessage;

    @Column(name = "refunded_amount", nullable = false,
            precision = 19, scale = 4)
    private BigDecimal refundedAmount = BigDecimal.ZERO;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;

        if (refundedAmount == null) {
            refundedAmount = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
