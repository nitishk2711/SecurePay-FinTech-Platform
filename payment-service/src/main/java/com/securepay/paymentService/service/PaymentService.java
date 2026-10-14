
package com.securepay.paymentService.service;

import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(
            CreatePaymentRequest request,
            String idempotencyKey
    );

    PaymentResponse getPayment(String paymentId);

    PaymentResponse getPaymentByOrder(String orderId);
}
