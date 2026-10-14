package com.securepay.transaction_service.repository;

import com.securepay.transaction_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository
        extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByTransactionId(String transactionId);

    List<Transaction> findByCustomerIdOrderByCreatedAtDesc(
            String customerId
    );

    List<Transaction> findByPaymentIdOrderByCreatedAtDesc(
            String paymentId
    );

    List<Transaction> findByOrderIdOrderByCreatedAtDesc(
            String orderId
    );

    List<Transaction> findByMerchantIdOrderByCreatedAtDesc(
            String merchantId
    );

    boolean existsByIdempotencyKey(String idempotencyKey);
}
