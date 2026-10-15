package com.securepay.merchant_service.service;

import com.securepay.merchant_service.enums.MerchantStatus;
import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.request.UpdateMerchantRequest;
import com.securepay.merchant_service.response.MerchantResponse;

public interface MerchantService {

    MerchantResponse onboard(MerchantRequest request);

    MerchantResponse getMerchant(String merchantId);

    MerchantResponse updateMerchant(
            String merchantId,
            UpdateMerchantRequest request
    );

    MerchantResponse updateStatus(
            String merchantId,
            MerchantStatus status
    );

    void rotateApiKey(String merchantId);
}
