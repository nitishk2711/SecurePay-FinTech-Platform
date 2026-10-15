package com.securepay.merchant_service.entity;

import com.securepay.merchant_service.enums.ApiKeyStatus;
import com.securepay.merchant_service.enums.Environment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "merchant_api_keys",
    indexes = {
        @Index(name = "idx_api_key_merchant", columnList = "merchant_id")
    }
)
@Getter
@Setter
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, unique = true)
    private String apiKey;

    // Store a password hash, not the original secret.
    @Column(nullable = false)
    private String secretHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Environment environment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApiKeyStatus status = ApiKeyStatus.ACTIVE;

    private Instant expiresAt;
    private Instant lastUsedAt;
    private Instant revokedAt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }
}
