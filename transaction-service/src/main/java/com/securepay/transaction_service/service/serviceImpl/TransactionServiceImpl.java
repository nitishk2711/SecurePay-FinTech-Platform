package com.securepay.transaction_service.service.serviceImpl;

import com.securepay.transaction_service.dto.CreateTransactionRequest;
import com.securepay.transaction_service.dto.RefundRequest;
import com.securepay.transaction_service.dto.TransactionResponse;
import com.securepay.transaction_service.entity.Transaction;
import com.securepay.transaction_service.entity.TransactionHistory;
import com.securepay.transaction_service.enums.TransactionStatus;
import com.securepay.transaction_service.enums.TransactionType;
import com.securepay.transaction_service.exception.DuplicateTransactionException;
import com.securepay.transaction_service.exception.InvalidTransactionStateException;
import com.securepay.transaction_service.exception.TransactionNotFoundException;
import com.securepay.transaction_service.repository.TransactionHistoryRepository;
import com.securepay.transaction_service.repository.TransactionRepository;
import com.securepay.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final TransactionHistoryRepository historyRepository;

    @Override
    public TransactionResponse createTransaction(
            CreateTransactionRequest request,
            String idempotencyKey) {

        if (request.getTransactionType() != TransactionType.PAYMENT) {
            throw new InvalidTransactionStateException(
                    "Create endpoint accepts PAYMENT transactions only"
            );
        }

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException(
                    "Idempotency-Key header is required"
            );
        }

        if (repository.existsByIdempotencyKey(idempotencyKey)) {
            throw new DuplicateTransactionException(
                    "This idempotency key has already been used"
            );
        }

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN_" + UUID.randomUUID());
        transaction.setPaymentId(request.getPaymentId());
        transaction.setOrderId(request.getOrderId());
        transaction.setCustomerId(request.getCustomerId());
        transaction.setMerchantId(request.getMerchantId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setIdempotencyKey(idempotencyKey);
        transaction.setRefundedAmount(BigDecimal.ZERO);

        repository.save(transaction);

        saveHistory(
                transaction,
                null,
                TransactionStatus.PENDING,
                "Transaction created",
                "PAYMENT_SERVICE"
        );

        return toResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(String transactionId) {
        return toResponse(findTransaction(transactionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getCustomerTransactions(
            String customerId) {

        return repository
                .findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getPaymentTransactions(
            String paymentId) {

        return repository
                .findByPaymentIdOrderByCreatedAtDesc(paymentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getMerchantTransactions(
            String merchantId) {

        return repository
                .findByMerchantIdOrderByCreatedAtDesc(merchantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TransactionResponse updateTransactionStatus(
            String transactionId,
            TransactionStatus newStatus,
            String processorTransactionId,
            String failureCode,
            String failureMessage) {

        Transaction transaction = findTransaction(transactionId);
        TransactionStatus currentStatus = transaction.getStatus();

        validateTransition(currentStatus, newStatus);

        transaction.setStatus(newStatus);

        if (processorTransactionId != null
                && !processorTransactionId.isBlank()) {
            transaction.setProcessorTransactionId(
                    processorTransactionId
            );
        }

        if (newStatus == TransactionStatus.FAILED) {
            transaction.setFailureCode(failureCode);
            transaction.setFailureMessage(failureMessage);
        }

        if (newStatus == TransactionStatus.SUCCESS
                || newStatus == TransactionStatus.FAILED) {
            transaction.setCompletedAt(java.time.Instant.now());
        }

        saveHistory(
                transaction,
                currentStatus,
                newStatus,
                failureMessage != null
                        ? failureMessage
                        : "Transaction status updated",
                "PAYMENT_PROCESSOR"
        );

        return toResponse(transaction);
    }

    @Override
    public TransactionResponse reverseTransaction(String transactionId) {

        Transaction transaction = findTransaction(transactionId);

        if (transaction.getStatus() != TransactionStatus.SUCCESS) {
            throw new InvalidTransactionStateException(
                    "Only successful transactions can be reversed"
            );
        }

        if (transaction.getRefundedAmount().signum() > 0) {
            throw new InvalidTransactionStateException(
                    "A transaction with refunds cannot be reversed"
            );
        }

        TransactionStatus oldStatus = transaction.getStatus();

        transaction.setStatus(TransactionStatus.REVERSED);

        saveHistory(
                transaction,
                oldStatus,
                TransactionStatus.REVERSED,
                "Transaction reversal requested",
                "INTERNAL_SERVICE"
        );

        return toResponse(transaction);
    }

    @Override
    public TransactionResponse refundTransaction(
            String transactionId,
            RefundRequest request) {

        Transaction original = findTransaction(transactionId);

        if (original.getStatus() != TransactionStatus.SUCCESS
                && original.getStatus()
                    != TransactionStatus.PARTIALLY_REFUNDED) {
            throw new InvalidTransactionStateException(
                    "Only successful or partially refunded transactions "
                            + "can be refunded"
            );
        }

        BigDecimal refundAmount = request.getAmount();

        if (refundAmount == null || refundAmount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Refund amount must be greater than zero"
            );
        }

        BigDecimal remaining = original.getAmount()
                .subtract(original.getRefundedAmount());

        if (refundAmount.compareTo(remaining) > 0) {
            throw new InvalidTransactionStateException(
                    "Refund amount exceeds the remaining refundable amount"
            );
        }

        Transaction refund = new Transaction();

        refund.setTransactionId("TXN_" + UUID.randomUUID());
        refund.setPaymentId(original.getPaymentId());
        refund.setOrderId(original.getOrderId());
        refund.setCustomerId(original.getCustomerId());
        refund.setMerchantId(original.getMerchantId());
        refund.setAmount(refundAmount);
        refund.setCurrency(original.getCurrency());
        refund.setTransactionType(TransactionType.REFUND);
        refund.setStatus(TransactionStatus.PENDING);
        refund.setParentTransactionId(original.getTransactionId());
        refund.setRefundedAmount(BigDecimal.ZERO);

        repository.save(refund);

        saveHistory(
                refund,
                null,
                TransactionStatus.PENDING,
                request.getReason() != null
                        ? request.getReason()
                        : "Refund requested",
                "INTERNAL_SERVICE"
        );

        return toResponse(refund);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionHistory> getTransactionHistory(
            String transactionId) {

        findTransaction(transactionId);

        return historyRepository
                .findByTransactionIdOrderByCreatedAtAsc(transactionId);
    }

    private Transaction findTransaction(String transactionId) {
        return repository.findByTransactionId(transactionId)
                .orElseThrow(
                        () -> new TransactionNotFoundException(
                                transactionId
                        )
                );
    }

    private void validateTransition(
            TransactionStatus current,
            TransactionStatus next) {

        boolean allowed = switch (current) {
            case CREATED ->
                    next == TransactionStatus.PENDING
                    || next == TransactionStatus.CANCELLED;

            case PENDING ->
                    next == TransactionStatus.PROCESSING
                    || next == TransactionStatus.SUCCESS
                    || next == TransactionStatus.FAILED
                    || next == TransactionStatus.CANCELLED;

            case PROCESSING ->
                    next == TransactionStatus.SUCCESS
                    || next == TransactionStatus.FAILED;

            case REFUND_PENDING ->
                    next == TransactionStatus.SUCCESS
                    || next == TransactionStatus.FAILED;

            case SUCCESS ->
                    next == TransactionStatus.REVERSED
                    || next == TransactionStatus.REFUND_PENDING
                    || next == TransactionStatus.PARTIALLY_REFUNDED
                    || next == TransactionStatus.REFUNDED;

            case PARTIALLY_REFUNDED ->
                    next == TransactionStatus.REFUND_PENDING
                    || next == TransactionStatus.REFUNDED;

            default -> false;
        };

        if (!allowed) {
            throw new InvalidTransactionStateException(
                    "Invalid status transition: "
                            + current + " -> " + next
            );
        }
    }

    private void saveHistory(
            Transaction transaction,
            TransactionStatus oldStatus,
            TransactionStatus newStatus,
            String reason,
            String createdBy) {

        TransactionHistory history = new TransactionHistory();

        history.setTransactionId(transaction.getTransactionId());
        history.setOldStatus(
                oldStatus == null ? null : oldStatus.name()
        );
        history.setNewStatus(newStatus.name());
        history.setReason(reason);
        history.setCreatedBy(createdBy);

        historyRepository.save(history);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getPaymentId(),
                transaction.getOrderId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getTransactionType(),
                transaction.getStatus(),
                transaction.getRefundedAmount(),
                transaction.getCreatedAt(),
                transaction.getCompletedAt()
        );
    }
}
