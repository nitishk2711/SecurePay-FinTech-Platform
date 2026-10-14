package com.securepay.merchant_service.repository;

import com.securepay.merchant_service.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository
        extends JpaRepository<Merchant, String> {

    Optional<Merchant> findByMerchantId(String merchantId);

    boolean existsByEmailIgnoreCase(String email);
}
