
package com.securepay.paymentService.components;

import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;

public interface PaymentProcessorClient {

    ProcessorResponse processPayment(ProcessorRequest request);
}
