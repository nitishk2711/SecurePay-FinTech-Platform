
package com.securepay.payment_processor_service.service.serviceimpl;

import com.securepay.payment_processor_service.component.ProcessorRouter;
import com.securepay.payment_processor_service.entity.ProcessorTransaction;
import com.securepay.payment_processor_service.enums.ProcessorStatus;
import com.securepay.payment_processor_service.repository.ProcessorTransactionRepository;
import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;
import com.securepay.payment_processor_service.service.ProcessorService;
import com.securepay.payment_processor_service.processor.PaymentProcessor;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {

    private final ProcessorTransactionRepository repository;
    private final ProcessorRouter processorRouter;

    @Override
    @Transactional
    public ProcessorResponse processPayment(ProcessorRequest request) {

        String processorName =
                request.getProcessorName().toUpperCase();

        // 1. Check whether this request was already processed.
        var existing = repository
                .findByProcessorNameAndIdempotencyKey(
                        processorName,
                        request.getIdempotencyKey()
                );

        if (existing.isPresent()) {
            ProcessorTransaction transaction = existing.get();

            // Never reuse a key for a different operation.
            boolean sameRequest =
                    transaction.getPaymentId().equals(request.getPaymentId())
                    && transaction.getMerchantId().equals(request.getMerchantId())
                    && transaction.getAmount().compareTo(request.getAmount()) == 0
                    && transaction.getCurrency().equals(request.getCurrency())
                    && transaction.getOperation() == request.getOperation();

            if (!sameRequest) {
                throw new IllegalArgumentException(
                        "Idempotency key was already used for a different request"
                );
            }

            return toResponse(transaction);
        }

        // 2. Select the requested processor.
        PaymentProcessor processor =
                processorRouter.route(processorName);

        // 3. Call the adapter.
        ProcessorResponse result = processor.process(request);

        // 4. Persist the result.
        ProcessorTransaction transaction = ProcessorTransaction.builder()
                .paymentId(request.getPaymentId())
                .merchantId(request.getMerchantId())
                .processorName(processorName)
                .idempotencyKey(request.getIdempotencyKey())
                .operation(request.getOperation())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(result.getStatus())
                .externalTransactionId(result.getExternalTransactionId())
                .responseCode(result.getResponseCode())
                .responseMessage(result.getMessage())
                .build();

        ProcessorTransaction saved = repository.save(transaction);

        // 5. Return the persisted transaction.
        return toResponse(saved);
    }

    private ProcessorResponse toResponse(
            ProcessorTransaction transaction) {

        return ProcessorResponse.builder()
                .paymentId(transaction.getPaymentId())
                .processorTransactionId(transaction.getId())
                .externalTransactionId(
                        transaction.getExternalTransactionId())
                .processorName(transaction.getProcessorName())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .status(transaction.getStatus())
                .responseCode(transaction.getResponseCode())
                .message(transaction.getResponseMessage())
                .build();
    }
}
