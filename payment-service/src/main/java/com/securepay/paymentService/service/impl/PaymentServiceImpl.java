
package com.securepay.paymentService.service.impl;

import com.securepay.paymentService.components.PaymentProcessorClient;
import com.securepay.paymentService.dto.CreatePaymentRequest;
import com.securepay.paymentService.dto.PaymentResponse;
import com.securepay.paymentService.dto.ProcessorRequest;
import com.securepay.paymentService.dto.ProcessorResponse;
import com.securepay.paymentService.entity.Payment;
import com.securepay.paymentService.entity.PaymentAttempt;
import com.securepay.paymentService.enums.PaymentStatus;
import com.securepay.paymentService.repository.PaymentAttemptRepository;
import com.securepay.paymentService.repository.PaymentRepository;
import com.securepay.paymentService.service.PaymentService;
import com.securepay.paymentService.exception.PaymentNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentAttemptRepository attemptRepository;
    private final PaymentProcessorClient processor;

    @Override
    public PaymentResponse createPayment(
            CreatePaymentRequest request,
            String idempotencyKey) {

        // 1. Return the existing payment for a repeated request.
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existing = paymentRepository
                    .findByIdempotencyKey(idempotencyKey);

            if (existing.isPresent()) {
                Payment payment = existing.get();

                // Do not allow the same key to represent
                // a different payment request.
                validateSameRequest(payment, request);

                return toResponse(payment);
            }
        }

        // 2. Create and persist the payment first.
        Payment payment = new Payment();
        payment.setPaymentId(
                "PAY_" + UUID.randomUUID()
                        .toString().replace("-", "")
        );
        payment.setOrderId(request.getOrderId());
        payment.setMerchantId(request.getMerchantId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setIdempotencyKey(idempotencyKey);

        paymentRepository.saveAndFlush(payment);

        // 3. Record the processor attempt.
        PaymentAttempt attempt = new PaymentAttempt();
        attempt.setPayment(payment);
        attempt.setAttemptNumber(1);
        attempt.setStatus("PROCESSING");

        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);
        attemptRepository.save(attempt);

        // 4. Call the external processor.
        ProcessorResponse result;

        try {
            ProcessorRequest processorRequest =
                    new ProcessorRequest(
                            payment.getPaymentId(),
                            payment.getAmount(),
                            payment.getCurrency(),
                            payment.getMethod()
                    );

            result = processor.processPayment(processorRequest);

        } catch (Exception ex) {
            // A timeout does not prove the payment failed.
            payment.setStatus(PaymentStatus.PENDING);
            paymentRepository.save(payment);

            attempt.setStatus("PENDING");
            attempt.setFailureMessage(
                    "Processor outcome is unknown; reconciliation required"
            );
            attemptRepository.save(attempt);

            return toResponse(payment);
        }

        // 5. Apply the processor result.
        if (result == null || result.status() == null) {
            payment.setStatus(PaymentStatus.PENDING);
            paymentRepository.save(payment);

            attempt.setStatus("PENDING");
            attempt.setFailureMessage(
                    "Processor returned an ambiguous response"
            );
            attemptRepository.save(attempt);

            return toResponse(payment);
        }

        attempt.setProcessorTransactionId(result.transactionId());
        attempt.setCompletedAt(java.time.LocalDateTime.now());

        switch (result.status().toUpperCase()) {
            case "SUCCESS" -> {
                payment.setStatus(PaymentStatus.CAPTURED);
                payment.setProcessorTransactionId(
                        result.transactionId()
                );
                attempt.setStatus("SUCCESS");
            }

            case "FAILED" -> {
                payment.setStatus(PaymentStatus.FAILED);
                payment.setFailureCode(result.failureCode());
                payment.setFailureMessage(result.message());
                attempt.setStatus("FAILED");
                attempt.setFailureCode(result.failureCode());
                attempt.setFailureMessage(result.message());
            }

            default -> {
                // Unknown statuses must not be treated as success.
                payment.setStatus(PaymentStatus.PENDING);
                attempt.setStatus("PENDING");
                attempt.setFailureMessage(
                        "Processor outcome requires verification"
                );
            }
        }

        paymentRepository.save(payment);
        attemptRepository.save(attempt);

        return toResponse(payment);
    }

    @Override
    public PaymentResponse getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(paymentId));

        return toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByOrder(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new PaymentNotFoundException(orderId));

        return toResponse(payment);
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getStatus().name(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getProcessorTransactionId()
        );
    }

    private void validateSameRequest(
            Payment payment,
            CreatePaymentRequest request) {

        boolean sameRequest =
                payment.getOrderId().equals(request.getOrderId())
                && payment.getMerchantId().equals(request.getMerchantId())
                && payment.getCustomerId().equals(request.getCustomerId())
                && payment.getAmount().compareTo(request.getAmount()) == 0
                && payment.getCurrency().equals(request.getCurrency())
                && payment.getMethod().equals(request.getMethod());

        if (!sameRequest) {
            throw new IllegalArgumentException(
                    "Idempotency key was already used with different parameters"
            );
        }
    }
}
