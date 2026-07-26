package com.securepay.payment_processor_service.controller;


import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.service.ProcessorService;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/processor")
@RequiredArgsConstructor
public class ProcessorController {

    private final ProcessorService service;

    @PostMapping("/pay")
    public ResponseEntity<?> process(@RequestBody ProcessorRequest request) {

        return ResponseEntity.ok(service.processPayment(request));

    }

}
