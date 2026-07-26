package com.securepay.paymentService.components;

import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-processor-service")
public interface PaymentProcessorClient {

    @PostMapping("/api/processor/pay")
    ProcessorResponse processPayment(@RequestBody ProcessorRequest request);

}