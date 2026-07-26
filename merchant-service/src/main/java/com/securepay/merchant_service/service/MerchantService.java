package com.securepay.merchant_service.service;

import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.response.MerchantResponse;

public interface MerchantService {

    MerchantResponse onboard(MerchantRequest request);

    MerchantResponse getMerchant(String merchantId);

    void rotateApiKey(String merchantId);

}