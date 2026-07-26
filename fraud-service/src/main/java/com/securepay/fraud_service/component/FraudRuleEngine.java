package com.securepay.fraud_service.component;

import com.securepay.fraud_service.dto.FraudRequest;
import com.securepay.fraud_service.dto.FraudResponse;
import org.springframework.stereotype.Component;

@Component
public class FraudRuleEngine {

    public FraudResponse check(FraudRequest request) {
        int score = 0;
        String reason = "";

// Rule 1
        if (request.getAmount() > 50000) {
            score += 50;
            reason = "High value transaction";
        }


// Rule 2
        if (request.getAmount() > 100000) {
            score += 30;
            reason = "Very high amount";
        }

        String status;

        if (score >= 70) {
            status = "BLOCKED";
        } else if (score >= 30) {
            status = "REVIEW";
        } else {
            status = "APPROVED";
        }

        return new FraudResponse(score, status, reason);
    }
}
