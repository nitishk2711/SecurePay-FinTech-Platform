package com.securepay.merchant_service.exception;

public class MerchantNotFoundException
        extends RuntimeException {

    public MerchantNotFoundException(String merchantId) {
        super("Merchant not found: " + merchantId);
    }
}
