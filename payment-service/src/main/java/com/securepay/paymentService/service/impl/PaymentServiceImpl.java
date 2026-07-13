package com.securepay.paymentService.service.impl;

import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.enums.PaymentStatus;
import com.securepay.paymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentServiceImpl {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayments(Payment payment) {
        payment.setStatus(PaymentStatus.CREATED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment autorizePayment(String id) {
        return paymentRepository.findById(id);
    }

    public Payment capturePayment(String id) {
        return paymentRepository.findById(id);
    }

    public Payment refundPayment(String id) {
        return paymentRepository.findById(id);
    }

    public Payment getPaymentDetails(String id) {
        return paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"))
    }

    public List<Payment> getOrderPayment(String id, String orderId){
        return paymentRepository.findById(id);
    }



}
