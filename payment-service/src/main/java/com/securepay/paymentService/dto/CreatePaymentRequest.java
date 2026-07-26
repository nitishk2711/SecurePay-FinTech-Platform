package com.securepay.paymentService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String merchantId;

    @NotBlank
    private String customerId;

    @NotNull
    private BigDecimal amount;

    private String currency;
    private String method;
}