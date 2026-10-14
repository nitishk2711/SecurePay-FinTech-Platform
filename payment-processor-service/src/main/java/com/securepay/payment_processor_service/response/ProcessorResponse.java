
package com.securepay.payment_processor_service.response;

import com.securepay.payment_processor_service.enums.ProcessorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorResponse {

    private UUID paymentId;

    private UUID processorTransactionId;

    private String externalTransactionId;

    private String processorName;

    private BigDecimal amount;

    private String currency;

    private ProcessorStatus status;

    private String responseCode;

    private String message;
}
