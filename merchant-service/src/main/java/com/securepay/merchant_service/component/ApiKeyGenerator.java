package com.securepay.merchant_service.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApiKeyGenerator {

    public String generateApiKey() {
        return "sk_test_" + UUID.randomUUID().toString().replace("-", "");
    }

    public String generateSecretKey() {
        return "sec_" + UUID.randomUUID().toString().replace("-", "");
    }
}