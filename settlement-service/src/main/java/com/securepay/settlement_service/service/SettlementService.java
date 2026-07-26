package com.securepay.settlement_service.service;

import com.securepay.settlement_service.dto.CreateSettlementRequest;
import com.securepay.settlement_service.dto.SettlementResponse;
import com.securepay.settlement_service.entity.Settlement;

import java.util.List;

public interface SettlementService {

    SettlementResponse createSettlement(CreateSettlementRequest request);

    SettlementResponse processSettlement(String settlementId);

    List<Settlement> getMerchantSettlements(String merchantId);

}
