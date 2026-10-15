
package com.securepay.settlement_service.client;

import com.securepay.settlement_service.dto.EligiblePayment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentClient {

    List<EligiblePayment> getEligiblePayments(
        String merchantId,
        LocalDate periodStart,
        LocalDate periodEnd,
        String currency
    );
}
