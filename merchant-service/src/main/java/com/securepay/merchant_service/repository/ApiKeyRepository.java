package com.securepay.merchant_service.repository;

import com.securepay.merchant_service.entity.ApiKey;
import com.securepay.merchant_service.entity.Merchant;
import com.securepay.merchant_service.enums.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository
        extends JpaRepository<ApiKey, String> {

    List<ApiKey> findByMerchant_Id(String merchantId);

    Optional<ApiKey> findByApiKey(String apiKey);

    List<ApiKey> findByMerchant_IdAndEnvironment(
            String merchantId,
            Environment environment
    );

    boolean existsByApiKey(String apiKey);
}
