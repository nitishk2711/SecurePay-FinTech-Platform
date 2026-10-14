
package com.securepay.payment_processor_service.request;

import com.securepay.payment_processor_service.enums.ProcessorOperation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProcessorRequest {

    @NotNull
    private UUID paymentId;

    @NotNull
    private UUID merchantId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String currency;

    @NotNull
    private ProcessorOperation operation;

    @NotBlank
    @Size(max = 100)
    private String idempotencyKey;

    @NotBlank
    private String processorName;
}
