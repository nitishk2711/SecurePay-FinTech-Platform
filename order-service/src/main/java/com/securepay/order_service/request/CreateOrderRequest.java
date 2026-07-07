package com.securepay.order_service.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {

    private String merchantId;

    private String customerId;

    private BigDecimal amount;

    private String currency;

    private String description;
}