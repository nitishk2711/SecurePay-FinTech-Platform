
package com.securepay.settlement_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record CreateSettlementRequest(

    @NotBlank
    String merchantId,

    @NotNull
    @PastOrPresent
    LocalDate periodStart,

    @NotNull
    @PastOrPresent
    LocalDate periodEnd,

    @NotBlank
    String currency

) {}
