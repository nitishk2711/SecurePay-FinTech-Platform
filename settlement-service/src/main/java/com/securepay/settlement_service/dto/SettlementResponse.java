
package com.securepay.settlement_service.dto;

import com.securepay.settlement_service.enums.SettlementStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SettlementResponse(
    String settlementId,
    String merchantId,
    BigDecimal grossAmount,
    BigDecimal feeAmount,
    BigDecimal adjustmentAmount,
    BigDecimal netAmount,
    String currency,
    Integer transactionCount,
    SettlementStatus status,
    LocalDate settlementDate,
    String payoutId
) {}
