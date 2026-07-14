package com.securepay.paymentService.service;

import com.securepay.paymentService.components.PaymentIdGenerator;
import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;
import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.enums.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {


    Payment createPayments(CreatePaymentRequest request);

    Payment refundPayment(String PaymentId);
//    Payment autorizePayment(String id);
//
//    Payment capturePayment(String id);
//
//    Payment refundPayment(String id);
//
//    Payment setlePayment(String id);
//
//    Payment failedPayment(String id);
//
//    Payment getPaymentDetails(String id);
//
//    List<Payment> getOrderPayment(String orderId);
}
