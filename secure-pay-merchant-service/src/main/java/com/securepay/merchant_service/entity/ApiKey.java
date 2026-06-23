package com.securepay.merchant_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String merchantId;

    @Column(unique = true)
    private String apiKey;

    private boolean active;

    private LocalDateTime createdAt;


}
