package com.securepay.order_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String merchantId;

    @NotBlank
    private String customerId;

    @NotNull
    private BigDecimal amount;

    private String currency;

}