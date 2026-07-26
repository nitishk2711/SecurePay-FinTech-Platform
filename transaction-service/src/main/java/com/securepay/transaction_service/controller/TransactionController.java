package com.securepay.transaction_service.controller;

import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateTransactionRequest request) {
        return ResponseEntity.ok(
                service.createTransaction(request)
        );
    }


    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> history(@PathVariable String customerId) {
        return ResponseEntity.ok(
                service.getCustomerTransactions(customerId)
        );
    }


    @PostMapping("/{transactionId}/reverse")
    public ResponseEntity<?> reverse(@PathVariable String transactionId) {
        service.reverseTransaction(transactionId);
        return ResponseEntity.ok("Transaction Reversed");
    }
}
