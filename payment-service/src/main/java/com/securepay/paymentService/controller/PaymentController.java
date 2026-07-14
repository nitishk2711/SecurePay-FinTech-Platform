package com.securepay.paymentService.controller;

import com.securepay.paymentService.dto.ApiResponseDto;
import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/createPayment")
    public ResponseEntity<ApiResponseDto<Payment>> createPayments(@RequestBody CreatePaymentRequest request) {
        try {
            Payment payment = paymentService.createPayments(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "Payment Created successfully", payment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(400, e.getMessage(), null));
        }
    }

    @PostMapping("/refundPayment/{paymentId}")
    public ResponseEntity<ApiResponseDto<Payment>> refundPayment(@PathVariable String paymentId) {
        try {
            Payment payment = paymentService.refundPayment(paymentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "Payment Refunded successfully", payment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(400, e.getMessage(), null));
        }
    }
}
