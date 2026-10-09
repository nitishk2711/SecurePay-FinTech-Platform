
package com.securepay.payment_processor_service.component;

import com.securepay.payment_processor_service.processor.PaymentProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessorRouter {

    private final List<PaymentProcessor> processors;

    public ProcessorRouter(List<PaymentProcessor> processors) {
        this.processors = processors;
    }

    public PaymentProcessor route(String processorName) {

        return processors.stream()
                .filter(processor ->
                        processor.supports(processorName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported processor: " + processorName
                        ));
    }
}
