package com.securepay.merchant_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantRequest {

    @NotBlank
    private String businessName;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private String gstNumber;

    private String address;


}