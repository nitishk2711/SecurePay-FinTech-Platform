package com.securepay.fraud_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FraudRequest {

    private String paymentId;

    private String customerId;

    private Double amount;

}
