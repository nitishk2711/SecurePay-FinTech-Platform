package com.securepay.paymentService.components;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class PaymentIdGenerator {
    public String generatePaymentId() {
        String random = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "PAY_" + random;
    }
}
