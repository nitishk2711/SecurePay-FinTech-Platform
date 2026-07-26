package com.securepay.merchant_service.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String merchantId;

    private String businessName;

    private String email;

    private String phone;

    private String gstNumber;

    private String address;

    private String status;


}