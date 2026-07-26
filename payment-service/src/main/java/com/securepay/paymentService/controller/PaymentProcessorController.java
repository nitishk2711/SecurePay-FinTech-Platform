package com.securepay.paymentService.controller;

import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/processor")
public class PaymentProcessorController {

    @PostMapping("/pay")
    public ProcessorResponse processPayment(@RequestBody ProcessorRequest request) {

        return new ProcessorResponse(
                "TXN_" + UUID.randomUUID().toString().substring(0, 8),
                "SUCCESS",
                "Payment processed successfully"
        );
    }
}