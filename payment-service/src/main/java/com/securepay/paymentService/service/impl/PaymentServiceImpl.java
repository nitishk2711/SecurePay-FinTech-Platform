package com.securepay.paymentService.service.impl;

import com.securepay.paymentService.components.PaymentIdGenerator;
import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;
import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.enums.PaymentStatus;
import com.securepay.paymentService.repository.PaymentRepository;
import com.securepay.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.securepay.paymentService.enums.PaymentStatus.CREATED;
import static com.securepay.paymentService.enums.PaymentStatus.REFUNDED;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentIdGenerator paymentId;

    @Override
    public Payment createPayments(CreatePaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setMerchantId(request.getMerchantId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getPaymentMethod());
        payment.setPaymentId(paymentId.generatePaymentId());
        payment.setStatus(CREATED);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOrderId(request.getOrderId());

//        paymentResponse.setMerchantId(request.getMerchantId());
//        paymentResponse.setCustomerId(request.getCustomerId());
        paymentResponse.setAmount(request.getAmount());
        paymentResponse.setPaymentMethod(request.getPaymentMethod());
        paymentResponse.setStatus(CREATED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment  refundPayment(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        if (payment == null) {
            throw new RuntimeException("Payment not found");
        }
        payment.setStatus(REFUNDED);
        payment.setUpdatedAt(new Date());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getallPayments() {
        return paymentRepository.findAll();
    }
//    @Override
//    public Payment autorizePayment(String id) {
//
//        Payment payment = getPaymentDetails(id);
//        payment.setStatus(PaymentStatus.AUTHORIZED);
//        return paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment capturePayment(String id) {
//        Payment payment = getPaymentDetails(id);
//        payment.setStatus(PaymentStatus.CAPTURED);
//        return paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment refundPayment(String id) {
//        Payment payment = getPaymentDetails(id);
//        payment.setStatus(PaymentStatus.REFUNDED);
//        return paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment setlePayment(String id) {
//        Payment payment = getPaymentDetails(id);
//        payment.setStatus(PaymentStatus.SETTLED);
//        return paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment failedPayment(String id) {
//        Payment payment = getPaymentDetails(id);
//        payment.setStatus(PaymentStatus.FAILED);
//        return paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment getPaymentDetails(String id) {
//        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
//    }
//
//    @Override
//    public List<Payment> getOrderPayment(String orderId) {
//        return paymentRepository.findByOrderId(orderId);
//    }


}
