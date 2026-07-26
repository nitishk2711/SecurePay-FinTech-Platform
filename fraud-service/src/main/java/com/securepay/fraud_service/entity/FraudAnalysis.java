package com.securepay.fraud_service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "fraud_analysis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FraudAnalysis {

    @Id
    private String id;

    private String paymentId;

    private String customerId;

    private Double amount;

    private Integer riskScore;

    private String status;

    private String reason;
}
