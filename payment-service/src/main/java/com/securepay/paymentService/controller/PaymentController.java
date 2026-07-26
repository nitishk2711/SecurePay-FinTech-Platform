package com.securepay.paymentService.controller;

import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;
import com.securepay.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody CreatePaymentRequest request) {
        return ResponseEntity.ok(service.createPayment(request));
    }
}
