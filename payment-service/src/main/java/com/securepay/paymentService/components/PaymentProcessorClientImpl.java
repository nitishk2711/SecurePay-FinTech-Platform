package com.securepay.paymentService.components;

import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorClientImpl implements PaymentProcessorClient {

    @Override
    public ProcessorResponse processPayment(ProcessorRequest request) {
        // Implement your payment processor communication here
        throw new UnsupportedOperationException(
            "Payment processor integration is not implemented yet"
        );
    }
}