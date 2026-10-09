
package com.securepay.payment_processor_service.processor;

import com.securepay.payment_processor_service.enums.ProcessorOperation;
import com.securepay.payment_processor_service.enums.ProcessorStatus;
import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockProcessor implements PaymentProcessor {

    @Override
    public boolean supports(String processorName) {
        return "MOCK".equalsIgnoreCase(processorName);
    }

    @Override
    public ProcessorResponse process(ProcessorRequest request) {

        ProcessorStatus status;
        String code;
        String message;

        if (request.getOperation() == ProcessorOperation.AUTHORIZE
                || request.getOperation() == ProcessorOperation.SALE) {

            status = ProcessorStatus.AUTHORIZED;
            code = "MOCK_APPROVED";
            message = "Mock authorization approved";

        } else if (request.getOperation() == ProcessorOperation.CAPTURE) {

            status = ProcessorStatus.CAPTURED;
            code = "MOCK_CAPTURED";
            message = "Mock capture completed";

        } else if (request.getOperation() == ProcessorOperation.VOID) {

            status = ProcessorStatus.VOIDED;
            code = "MOCK_VOIDED";
            message = "Mock void completed";

        } else {

            status = ProcessorStatus.REFUNDED;
            code = "MOCK_REFUNDED";
            message = "Mock refund completed";
        }

        return ProcessorResponse.builder()
                .paymentId(request.getPaymentId())
                .processorTransactionId(UUID.randomUUID())
                .externalTransactionId(
                        "mock_" + UUID.randomUUID()
                )
                .processorName("MOCK")
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(status)
                .responseCode(code)
                .message(message)
                .build();
    }
}
