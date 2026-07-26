package com.securepay.settlement_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateSettlementRequest {

    private String merchantId;

    private BigDecimal amount;

    private Integer transactionCount;

}
