package com.securepay.settlement_service.service.serviceImpl;

import com.securepay.settlement_service.dto.CreateSettlementRequest;
import com.securepay.settlement_service.dto.SettlementResponse;
import com.securepay.settlement_service.entity.Settlement;
import com.securepay.settlement_service.enums.SettlementStatus;
import com.securepay.settlement_service.repository.SettlementRepository;
import com.securepay.settlement_service.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository repository;

    @Override
    public SettlementResponse createSettlement(CreateSettlementRequest request) {

        Settlement settlement = new Settlement();
        settlement.setSettlementId("SET_" + UUID.randomUUID().toString().substring(0, 8));
        settlement.setMerchantId(request.getMerchantId());
        settlement.setAmount(request.getAmount());
        settlement.setTransactionCount(request.getTransactionCount());
        settlement.setStatus(SettlementStatus.PENDING.name());
        repository.save(settlement);

        return new SettlementResponse(
                settlement.getSettlementId(),
                settlement.getAmount(),
                settlement.getStatus()
        );
    }


    @Override
    public SettlementResponse processSettlement(String settlementId) {

        Settlement settlement = repository.findBySettlementId(settlementId).orElseThrow();
        settlement.setStatus(SettlementStatus.PROCESSING.name());
        repository.save(settlement);

/*

Here later:

Call banking service

Transfer money

*/

        settlement.setStatus(SettlementStatus.COMPLETED.name());
        repository.save(settlement);

        return new SettlementResponse(
                settlement.getSettlementId(),
                settlement.getAmount(),
                settlement.getStatus()
        );
    }

    @Override
    public List<Settlement> getMerchantSettlements(String merchantId) {
        return repository.findByMerchantId(merchantId);
    }
}
