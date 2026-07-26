package com.securepay.fraud_service.service.serviceImpl;

import com.securepay.fraud_service.component.FraudRuleEngine;
import com.securepay.fraud_service.dto.FraudRequest;
import com.securepay.fraud_service.dto.FraudResponse;
import com.securepay.fraud_service.entity.FraudAnalysis;
import com.securepay.fraud_service.repository.FraudRepository;
import com.securepay.fraud_service.service.FraudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudServiceImpl implements FraudService {

    private final FraudRepository repository;
    private final FraudRuleEngine engine;

    @Override
    public FraudResponse analyze(FraudRequest request) {

        FraudResponse response = engine.check(request);

        FraudAnalysis analysis = new FraudAnalysis();
        analysis.setPaymentId(request.getPaymentId());
        analysis.setCustomerId(request.getCustomerId());
        analysis.setAmount(request.getAmount());
        analysis.setRiskScore(response.getRiskScore());
        analysis.setStatus(response.getStatus());
        analysis.setReason(response.getReason());
        repository.save(analysis);
        return response;
    }


    @Override
    public FraudAnalysis getResult(String paymentId) {
        return repository.findByPaymentId(paymentId).orElseThrow();
    }
}
