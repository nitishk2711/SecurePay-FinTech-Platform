
package com.securepay.paymentService.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String merchantId;

    @NotBlank
    private String customerId;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 15, fraction = 4)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String currency;

    @NotBlank
    private String method;
}
