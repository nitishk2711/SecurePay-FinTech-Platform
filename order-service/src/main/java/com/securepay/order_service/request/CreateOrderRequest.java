package com.securepay.order_service.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {

    @NotBlank(message = "Merchant ID is required")
    private String merchantId;

    private String customerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "Amount must be greater than zero"
    )
    @Digits(
        integer = 15,
        fraction = 4,
        message = "Invalid amount precision"
    )
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(
        regexp = "^[A-Z]{3}$",
        message = "Currency must be a three-letter uppercase code"
    )
    private String currency;

    @Size(max = 255, message = "Description is too long")
    private String description;
}
