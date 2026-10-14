package com.securepay.transaction_service.repository;

import com.securepay.transaction_service.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionHistoryRepository
        extends JpaRepository<TransactionHistory, UUID> {

    List<TransactionHistory> findByTransactionIdOrderByCreatedAtAsc(
            String transactionId
    );
}
