
package com.securepay.settlement_service.client;

import com.securepay.settlement_service.dto.PayoutRequest;
import com.securepay.settlement_service.dto.PayoutResponse;

public interface PayoutClient {

    PayoutResponse submitPayout(PayoutRequest request);

    PayoutResponse getPayoutStatus(String payoutId);
}
