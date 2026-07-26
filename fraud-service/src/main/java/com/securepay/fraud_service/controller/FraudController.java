package com.securepay.fraud_service.controller;

import com.securepay.fraud_service.dto.FraudRequest;
import com.securepay.fraud_service.service.FraudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
public class FraudController {

    private final FraudService service;

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody FraudRequest request) {
        return ResponseEntity.ok(service.analyze(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> result(@PathVariable String paymentId) {
        return ResponseEntity.ok(service.getResult(paymentId));
    }
}
