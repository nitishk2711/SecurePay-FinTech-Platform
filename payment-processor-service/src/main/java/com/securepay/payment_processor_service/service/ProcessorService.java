
package com.securepay.payment_processor_service.service;

import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;

public interface ProcessorService {

    ProcessorResponse processPayment(ProcessorRequest request);
}
