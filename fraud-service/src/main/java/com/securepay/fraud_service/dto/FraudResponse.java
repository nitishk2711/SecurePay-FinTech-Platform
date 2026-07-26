package com.securepay.fraud_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FraudResponse {

    private Integer riskScore;

    private String status;

    private String reason;

}
