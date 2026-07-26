package com.securepay.transaction_service.entity;


import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String transactionId;

    private String paymentId;

    private String orderId;

    private String customerId;

    private String merchantId;

    private BigDecimal amount;

    private String transactionType;

    private String status;

}
