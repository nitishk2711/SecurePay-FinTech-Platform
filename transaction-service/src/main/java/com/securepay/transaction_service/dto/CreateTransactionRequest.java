package com.securepay.transaction_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequest {

    private String paymentId;

    private String orderId;

    private String customerId;

    private String merchantId;

    private BigDecimal amount;

    private String transactionType;

}
