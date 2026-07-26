package com.securepay.ledger_service.controller;

import com.securepay.ledger_service.request.LedgerRequest;
import com.securepay.ledger_service.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LedgerRequest request) {
        return ResponseEntity.ok(service.createLedger(request));
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity<?> history(@PathVariable String transactionId) {
        return ResponseEntity.ok(service.getHistory(transactionId));
    }


}
