
package com.securepay.settlement_service.repository;

import com.securepay.settlement_service.entity.SettlementStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementHistoryRepository
        extends JpaRepository<SettlementStatusHistory, Long> {

    List<SettlementStatusHistory>
        findBySettlementIdOrderByCreatedAtAsc(String settlementId);
}
