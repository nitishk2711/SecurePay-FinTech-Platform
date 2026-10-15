
package com.securepay.settlement_service.entity;

import com.securepay.settlement_service.enums.SettlementStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "settlements",
    indexes = {
        @Index(name = "idx_settlement_merchant",
               columnList = "merchant_id"),
        @Index(name = "idx_settlement_status",
               columnList = "status")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_settlement_id",
            columnNames = "settlement_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "settlement_id",
            nullable = false, updatable = false, length = 40)
    private String settlementId;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal grossAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal feeAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal adjustmentAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal netAmount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private Integer transactionCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SettlementStatus status;

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate settlementDate;

    private String payoutId;
    private String bankReference;

    private String failureReason;

    @Column(nullable = false)
    private Integer retryCount = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
