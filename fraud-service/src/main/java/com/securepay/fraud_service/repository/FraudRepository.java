package com.securepay.fraud_service.repository;

import com.securepay.fraud_service.entity.FraudAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraudRepository extends MongoRepository<FraudAnalysis, String> {

    Optional<FraudAnalysis> findByPaymentId(String paymentId);
}
