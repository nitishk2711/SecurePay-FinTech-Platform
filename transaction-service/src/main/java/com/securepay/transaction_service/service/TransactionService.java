package com.securepay.transaction_service.service;

import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.dto.TransactionResponse;
import com.securepay.transaction_service.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(CreateTransactionRequest request);

    List<Transaction> getCustomerTransactions(String customerId);

    void reverseTransaction(String transactionId);

}
