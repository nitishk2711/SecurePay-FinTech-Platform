package com.securepay.order_service.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {

    private String orderId;

    private String merchantId;

    private String customerId;

    private BigDecimal amount;

    private String currency;

    private String description;

    private String status;
}