package com.securepay.settlement_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SettlementResponse {

    private String settlementId;

    private BigDecimal amount;

    private String status;
}
