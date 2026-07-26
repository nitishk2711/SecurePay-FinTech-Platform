package com.securepay.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TransactionResponse {

    private String transactionId;

    private String status;

    private BigDecimal amount;


}
