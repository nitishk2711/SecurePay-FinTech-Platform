package com.securepay.paymentService.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String paymentId;

    private String orderId;

    private String merchantId;

    private String customerId;

    private BigDecimal amount;

    private String currency;

    private String method;

    private String status;

}
