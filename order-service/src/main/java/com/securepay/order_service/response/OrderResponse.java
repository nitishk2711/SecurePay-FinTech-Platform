package com.securepay.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String status;

}