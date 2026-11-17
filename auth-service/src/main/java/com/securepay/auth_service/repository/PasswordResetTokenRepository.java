package com.securepay.auth_service.repository;

import com.securepay.auth_service.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken>
    findTopByEmailAndUsedFalseOrderByIdDesc(String email);

    Optional<PasswordResetToken>
    findByResetTokenHashAndUsedFalse(String resetTokenHash);
}