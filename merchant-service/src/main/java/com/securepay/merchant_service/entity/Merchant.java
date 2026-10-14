package com.securepay.merchant_service.entity;

import com.securepay.merchant_service.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "merchants",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "merchant_id"),
        @UniqueConstraint(columnNames = "email")
    }
)
@Getter
@Setter
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "merchant_id", nullable = false, unique = true)
    private String merchantId;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status = MerchantStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
