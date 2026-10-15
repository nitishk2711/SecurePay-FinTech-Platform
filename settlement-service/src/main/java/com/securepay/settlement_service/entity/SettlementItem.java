
package com.securepay.settlement_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
    name = "settlement_items",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_settlement_payment",
            columnNames = {"settlement_id", "payment_id"}
        ),
        @UniqueConstraint(
            name = "uk_settlement_item_payment",
            columnNames = "payment_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class SettlementItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String settlementId;

    @Column(nullable = false, unique = true)
    private String paymentId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal grossAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal feeAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal netAmount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private String status;
}
