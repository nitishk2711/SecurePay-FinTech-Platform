package com.securepay.settlement_service.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String settlementId;

    private String merchantId;

    private BigDecimal amount;

    private Integer transactionCount;

    private String status;
}
