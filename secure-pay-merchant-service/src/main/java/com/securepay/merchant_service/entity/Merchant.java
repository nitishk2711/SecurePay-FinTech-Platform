package com.securepay.merchant_service.entity;

import com.securepay.merchant_service.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "merchants")
@Data
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String merchantCode;
    private String businessName;
    private String email;
    private Long phone;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;
    private LocalDateTime createAt;

}
