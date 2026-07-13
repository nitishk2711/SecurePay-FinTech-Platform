package com.securepay.paymentService.service;

import com.securepay.paymentService.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayments(Payment payment);
    Payment autorizePayment(String id);
    Payment capturePayment(String id);
    Payment refundPayment(String id);
    Payment getPaymentDetails(String id);
    List<Payment> getOrderPayment(String id, String orderId);
}
