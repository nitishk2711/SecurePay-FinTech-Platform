package com.securepay.merchant_service.repository;

import com.securepay.merchant_service.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey , String> {

}
