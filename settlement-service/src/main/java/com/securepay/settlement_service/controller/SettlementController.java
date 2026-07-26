package com.securepay.settlement_service.controller;

import com.securepay.settlement_service.dto.CreateSettlementRequest;
import com.securepay.settlement_service.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateSettlementRequest request) {
        return ResponseEntity.ok(service.createSettlement(request));
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<?> process(@PathVariable String id) {
        return ResponseEntity.ok(service.processSettlement(id));
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<?> merchantHistory(@PathVariable String merchantId) {
        return ResponseEntity.ok(service.getMerchantSettlements(merchantId));
    }
}
