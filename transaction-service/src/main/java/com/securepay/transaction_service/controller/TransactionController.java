
package com.securepay.transaction_service.controller;

import com.securepay.transaction_service.dto.ApiResponseDto;
import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.dto.RefundRequest;
import com.securepay.transaction_service.dto.TransactionResponse;
import com.securepay.transaction_service.entity.TransactionHistory;
import com.securepay.transaction_service.enums.TransactionStatus;
import com.securepay.transaction_service.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<TransactionResponse>> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {
        try {
            TransactionResponse response =
                    transactionService.createTransaction(
                            request,
                            idempotencyKey
                    );

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "Transaction created successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create transaction",
                            null
                    )
            );
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponseDto<TransactionResponse>> getTransaction(
            @PathVariable String transactionId) {
        try {
            TransactionResponse response =
                    transactionService.getTransaction(transactionId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve transaction",
                            null
                    )
            );
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponseDto<List<TransactionResponse>>> getCustomerTransactions(
            @PathVariable String customerId) {
        try {
            List<TransactionResponse> response =
                    transactionService.getCustomerTransactions(customerId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve customer transactions",
                            null
                    )
            );
        }
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<ApiResponseDto<List<TransactionResponse>>> getMerchantTransactions(
            @PathVariable String merchantId) {
        try {
            List<TransactionResponse> response =
                    transactionService.getMerchantTransactions(merchantId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve merchant transactions",
                            null
                    )
            );
        }
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<ApiResponseDto<List<TransactionResponse>>> getPaymentTransactions(
            @PathVariable String paymentId) {
        try {
            List<TransactionResponse> response =
                    transactionService.getPaymentTransactions(paymentId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve payment transactions",
                            null
                    )
            );
        }
    }

    @GetMapping("/{transactionId}/history")
    public ResponseEntity<ApiResponseDto<List<TransactionHistory>>> getTransactionHistory(
            @PathVariable String transactionId) {
        try {
            List<TransactionHistory> response =
                    transactionService.getTransactionHistory(transactionId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve transaction history",
                            null
                    )
            );
        }
    }

    @PostMapping("/{transactionId}/refund")
    public ResponseEntity<ApiResponseDto<TransactionResponse>> refundTransaction(
            @PathVariable String transactionId,
            @Valid @RequestBody RefundRequest request) {
        try {
            TransactionResponse response =
                    transactionService.refundTransaction(
                            transactionId,
                            request
                    );

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Refund processed successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to process refund",
                            null
                    )
            );
        }
    }

    @PostMapping("/{transactionId}/reverse")
    public ResponseEntity<ApiResponseDto<TransactionResponse>> reverseTransaction(
            @PathVariable String transactionId) {
        try {
            TransactionResponse response =
                    transactionService.reverseTransaction(transactionId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Transaction reversed successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to reverse transaction",
                            null
                    )
            );
        }
    }

    /*
     * Internal endpoint only.
     * Protect it with service-to-service authentication.
     */
    @PatchMapping("/{transactionId}/status")
    public ResponseEntity<ApiResponseDto<TransactionResponse>> updateStatus(
            @PathVariable String transactionId,
            @RequestParam TransactionStatus status,
            @RequestParam(required = false) String processorTransactionId,
            @RequestParam(required = false) String failureCode,
            @RequestParam(required = false) String failureMessage) {
        try {
            TransactionResponse response =
                    transactionService.updateTransactionStatus(
                            transactionId,
                            status,
                            processorTransactionId,
                            failureCode,
                            failureMessage
                    );

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Transaction status updated successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to update transaction status",
                            null
                    )
            );
        }
    }
}
