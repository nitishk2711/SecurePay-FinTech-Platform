package com.securepay.transaction_service.repository;

import com.securepay.transaction_service.document.TransactionMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMetadataRepository extends MongoRepository<TransactionMetadata, String> {

    List<TransactionMetadata> findByTransactionId(String transactionId);

}
