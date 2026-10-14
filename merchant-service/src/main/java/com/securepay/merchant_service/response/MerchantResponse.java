package com.securepay.merchant_service.response;

import com.securepay.merchant_service.enums.MerchantStatus;

public record MerchantResponse(
        String merchantId,
        String apiKey,
        String secretKey,
        MerchantStatus status
) {}
