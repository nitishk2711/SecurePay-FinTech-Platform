
package com.securepay.settlement_service.service.serviceImpl;

import com.securepay.settlement_service.client.PaymentClient;
import com.securepay.settlement_service.dto.CreateSettlementRequest;
import com.securepay.settlement_service.dto.EligiblePayment;
import com.securepay.settlement_service.dto.SettlementResponse;
import com.securepay.settlement_service.entity.Settlement;
import com.securepay.settlement_service.entity.SettlementItem;
import com.securepay.settlement_service.entity.SettlementStatusHistory;
import com.securepay.settlement_service.enums.SettlementStatus;
import com.securepay.settlement_service.repository.SettlementHistoryRepository;
import com.securepay.settlement_service.repository.SettlementItemRepository;
import com.securepay.settlement_service.repository.SettlementRepository;
import com.securepay.settlement_service.service.SettlementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository repository;
    private final SettlementItemRepository itemRepository;
    private final SettlementHistoryRepository historyRepository;
    private final PaymentClient paymentClient;

    @Override
    @Transactional
    public SettlementResponse createSettlement(
            CreateSettlementRequest request) {

        if (request.periodStart().isAfter(request.periodEnd())) {
            throw new IllegalArgumentException(
                "Period start must not be after period end");
        }

        List<EligiblePayment> payments =
            paymentClient.getEligiblePayments(
                request.merchantId(),
                request.periodStart(),
                request.periodEnd(),
                request.currency()
            );

        if (payments == null || payments.isEmpty()) {
            throw new IllegalArgumentException(
                "No eligible payments found for settlement");
        }

        BigDecimal gross = BigDecimal.ZERO;
        BigDecimal fees = BigDecimal.ZERO;

        for (EligiblePayment payment : payments) {
            if (payment.paymentId() == null
                    || payment.amount() == null
                    || payment.feeAmount() == null
                    || payment.amount().signum() <= 0
                    || payment.feeAmount().signum() < 0
                    || !request.currency().equalsIgnoreCase(
                        payment.currency())) {
                throw new IllegalArgumentException(
                    "Invalid eligible payment data");
            }

            if (itemRepository.existsByPaymentId(
                    payment.paymentId())) {
                throw new IllegalStateException(
                    "Payment already allocated: "
                        + payment.paymentId());
            }

            gross = gross.add(payment.amount());
            fees = fees.add(payment.feeAmount());
        }

        BigDecimal adjustments = BigDecimal.ZERO;
        BigDecimal net = gross.subtract(fees).add(adjustments);

        if (net.signum() < 0) {
            throw new IllegalStateException(
                "Net settlement amount cannot be negative");
        }

        Settlement settlement = new Settlement();
        settlement.setSettlementId(
            "SET_" + UUID.randomUUID());
        settlement.setMerchantId(request.merchantId());
        settlement.setGrossAmount(gross);
        settlement.setFeeAmount(fees);
        settlement.setAdjustmentAmount(adjustments);
        settlement.setNetAmount(net);
        settlement.setCurrency(
            request.currency().toUpperCase());
        settlement.setTransactionCount(payments.size());
        settlement.setPeriodStart(request.periodStart());
        settlement.setPeriodEnd(request.periodEnd());
        settlement.setSettlementDate(LocalDate.now());
        settlement.setStatus(SettlementStatus.PENDING);

        repository.save(settlement);

        for (EligiblePayment payment : payments) {
            SettlementItem item = new SettlementItem();
            item.setSettlementId(settlement.getSettlementId());
            item.setPaymentId(payment.paymentId());
            item.setGrossAmount(payment.amount());
            item.setFeeAmount(payment.feeAmount());
            item.setNetAmount(
                payment.amount().subtract(payment.feeAmount()));
            item.setCurrency(
                payment.currency().toUpperCase());
            item.setStatus("ALLOCATED");

            itemRepository.save(item);
        }

        recordHistory(
            settlement.getSettlementId(),
            null,
            SettlementStatus.PENDING,
            "Settlement created",
            "SYSTEM"
        );

        return toResponse(settlement);
    }

    @Override
    @Transactional
    public SettlementResponse processSettlement(
            String settlementId) {

        Settlement settlement = findSettlement(settlementId);

        if (settlement.getStatus() != SettlementStatus.PENDING) {
            throw new IllegalStateException(
                "Only PENDING settlements can be processed");
        }

        SettlementStatus oldStatus = settlement.getStatus();

        settlement.setStatus(SettlementStatus.PROCESSING);
        repository.save(settlement);

        recordHistory(
            settlementId,
            oldStatus,
            SettlementStatus.PROCESSING,
            "Settlement processing started",
            "SYSTEM"
        );

        // Next step: submit an idempotent payout request.
        // Do not mark COMPLETED until the payout provider
        // confirms successful transfer.

        return toResponse(settlement);
    }

    @Override
    @Transactional
    public SettlementResponse getSettlement(String settlementId) {
        return toResponse(findSettlement(settlementId));
    }

    @Override
    @Transactional
    public List<Settlement> getMerchantSettlements(
            String merchantId) {

        return repository.findByMerchantId(
            merchantId,
            org.springframework.data.domain.Pageable.unpaged()
        ).getContent();
    }

    private Settlement findSettlement(String settlementId) {
        return repository.findBySettlementId(settlementId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Settlement not found: " + settlementId));
    }

    private void recordHistory(
            String settlementId,
            SettlementStatus oldStatus,
            SettlementStatus newStatus,
            String reason,
            String actor) {

        SettlementStatusHistory history =
            new SettlementStatusHistory();

        history.setSettlementId(settlementId);
        history.setOldStatus(
            oldStatus == null ? null : oldStatus.name());
        history.setNewStatus(newStatus.name());
        history.setReason(reason);
        history.setActor(actor);

        historyRepository.save(history);
    }

    private SettlementResponse toResponse(
            Settlement settlement) {

        return new SettlementResponse(
            settlement.getSettlementId(),
            settlement.getMerchantId(),
            settlement.getGrossAmount(),
            settlement.getFeeAmount(),
            settlement.getAdjustmentAmount(),
            settlement.getNetAmount(),
            settlement.getCurrency(),
            settlement.getTransactionCount(),
            settlement.getStatus(),
            settlement.getSettlementDate(),
            settlement.getPayoutId()
        );
    }
}
