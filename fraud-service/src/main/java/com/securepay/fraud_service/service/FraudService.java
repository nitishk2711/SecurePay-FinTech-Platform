package com.securepay.fraud_service.service;

import com.securepay.fraud_service.dto.FraudRequest;
import com.securepay.fraud_service.dto.FraudResponse;
import com.securepay.fraud_service.entity.FraudAnalysis;

public interface FraudService {

    FraudResponse analyze(FraudRequest request);

    FraudAnalysis getResult(String paymentId);

}
