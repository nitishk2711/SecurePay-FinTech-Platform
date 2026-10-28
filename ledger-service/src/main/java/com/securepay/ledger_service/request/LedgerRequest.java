package com.securepay.ledger_service.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record LedgerRequest(

        @NotBlank @Size(max = 100) String transactionId,
        @NotBlank @Size(max = 150) String idempotencyKey,
        @NotBlank @Pattern(regexp = "^[A-Z]{3}$") String currency,
        @Size(max = 500) String description,
        @NotEmpty @Size(min = 2, max = 100) List<@Valid LedgerLineRequest> entries

) {
}