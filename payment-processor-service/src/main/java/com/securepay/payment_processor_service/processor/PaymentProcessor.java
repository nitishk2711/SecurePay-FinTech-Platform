
package com.securepay.payment_processor_service.processor;

import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;

public interface PaymentProcessor {

    boolean supports(String processorName);

    ProcessorResponse process(ProcessorRequest request);
}
