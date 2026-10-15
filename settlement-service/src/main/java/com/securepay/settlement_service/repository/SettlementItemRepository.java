
package com.securepay.settlement_service.repository;

import com.securepay.settlement_service.entity.SettlementItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementItemRepository
        extends JpaRepository<SettlementItem, Long> {

    List<SettlementItem> findBySettlementId(String settlementId);

    boolean existsByPaymentId(String paymentId);
}
