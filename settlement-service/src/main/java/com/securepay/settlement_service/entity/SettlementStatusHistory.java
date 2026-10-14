
package com.securepay.settlement_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "settlement_status_history")
@Getter
@Setter
@NoArgsConstructor
public class SettlementStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String settlementId;

    @Column(length = 20)
    private String oldStatus;

    @Column(nullable = false, length = 20)
    private String newStatus;

    private String reason;
    private String actor;
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
