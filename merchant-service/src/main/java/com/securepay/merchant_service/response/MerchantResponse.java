package com.securepay.merchant_service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MerchantResponse {

    private String merchantId;

    private String apiKey;

    private String secretKey;

    private String status;

}