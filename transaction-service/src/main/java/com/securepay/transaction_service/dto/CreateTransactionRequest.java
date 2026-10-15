package com.securepay.transaction_service.dto;

import com.securepay.transaction_service.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotBlank
    private String paymentId;

    @NotBlank
    private String orderId;

    @NotBlank
    private String customerId;

    @NotBlank
    private String merchantId;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 15, fraction = 4)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String currency;

    @NotNull
    private TransactionType transactionType;
}
