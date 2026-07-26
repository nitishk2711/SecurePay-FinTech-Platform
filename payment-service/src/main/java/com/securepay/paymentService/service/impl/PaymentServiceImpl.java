package com.securepay.paymentService.service.impl;

import com.securepay.paymentService.components.PaymentProcessorClient;
import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;
import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;
import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.enums.PaymentStatus;
import com.securepay.paymentService.repository.PaymentRepository;
import com.securepay.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentProcessorClient processor;

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        Payment payment = new Payment();
        payment.setPaymentId("PAY_" + UUID.randomUUID().toString().substring(0, 8));
        payment.setOrderId(request.getOrderId());
        payment.setMerchantId(request.getMerchantId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.CREATED.name());
        repository.save(payment);

/*

Later:

Call Payment Processor

*/

        ProcessorRequest processorRequest = new ProcessorRequest(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getMethod()
        );

        ProcessorResponse response = processor.processPayment(processorRequest);
        if (response.getStatus().equals("SUCCESS")) {
            payment.setStatus(PaymentStatus.CAPTURED.name());
        } else {
            payment.setStatus(PaymentStatus.FAILED.name());
        }
        repository.save(payment);

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getStatus(),
                payment.getAmount()
        );

    }

    @Override
    public PaymentResponse refund(String paymentId) {
        return null;
    }


}
