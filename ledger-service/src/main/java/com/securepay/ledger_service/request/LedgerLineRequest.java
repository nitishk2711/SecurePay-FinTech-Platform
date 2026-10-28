package com.securepay.ledger_service.request;

import com.securepay.ledger_service.enums.EntryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LedgerLineRequest(

        @NotBlank String accountId,

        @NotNull EntryType entryType,

        @NotNull @Positive BigDecimal amount,

        String description

) {
}