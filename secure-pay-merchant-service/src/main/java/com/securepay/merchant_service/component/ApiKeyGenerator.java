package com.securepay.merchant_service.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApiKeyGenerator {

    public String generate() {

        return "sk_live_" +
                UUID.randomUUID()
                        .toString()
                        .replace("-","");
    }
}