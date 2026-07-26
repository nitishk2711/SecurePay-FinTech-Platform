package com.securepay.transaction_service.service.serviceImpl;

import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.dto.TransactionResponse;
import com.securepay.transaction_service.entity.Transaction;
import com.securepay.transaction_service.enums.TransactionStatus;
import com.securepay.transaction_service.repository.TransactionRepository;
import com.securepay.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN_" + UUID.randomUUID().toString().substring(0, 8));
        transaction.setPaymentId(request.getPaymentId());
        transaction.setOrderId(request.getOrderId());
        transaction.setCustomerId(request.getCustomerId());
        transaction.setMerchantId(request.getMerchantId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setStatus(TransactionStatus.SUCCESS.name());
        repository.save(transaction);

        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getStatus(),
                transaction.getAmount()
        );
    }


    @Override
    public List<Transaction> getCustomerTransactions(String customerId) {
        return repository.findByCustomerId(customerId);
    }


    @Override
    public void reverseTransaction(String transactionId) {

        Transaction transaction = repository.findByTransactionId(transactionId).orElseThrow();
        transaction.setStatus(TransactionStatus.REVERSED.name());
        repository.save(transaction);
    }
}
