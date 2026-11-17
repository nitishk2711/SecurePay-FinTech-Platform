package com.securepay.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
@Data

public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otpHash;

    @Column(unique = true)
    private String resetTokenHash;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    private Integer attempts = 0;

    private boolean verified = false;

    private boolean used = false;
}
