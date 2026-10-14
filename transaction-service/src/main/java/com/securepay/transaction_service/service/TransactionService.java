package com.securepay.transaction_service.service;

import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.dto.RefundRequest;
import com.securepay.transaction_service.dto.TransactionResponse;
import com.securepay.transaction_service.entity.TransactionHistory;
import com.securepay.transaction_service.enums.TransactionStatus;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(
            CreateTransactionRequest request,
            String idempotencyKey
    );

    TransactionResponse getTransaction(String transactionId);

    List<TransactionResponse> getCustomerTransactions(String customerId);

    List<TransactionResponse> getPaymentTransactions(String paymentId);

    List<TransactionResponse> getMerchantTransactions(String merchantId);

    TransactionResponse updateTransactionStatus(
            String transactionId,
            TransactionStatus newStatus,
            String processorTransactionId,
            String failureCode,
            String failureMessage
    );

    TransactionResponse reverseTransaction(String transactionId);

    TransactionResponse refundTransaction(
            String transactionId,
            RefundRequest request
    );

    List<TransactionHistory> getTransactionHistory(String transactionId);
}
