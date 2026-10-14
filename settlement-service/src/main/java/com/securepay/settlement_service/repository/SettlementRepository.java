
package com.securepay.settlement_service.repository;

import com.securepay.settlement_service.entity.Settlement;
import com.securepay.settlement_service.enums.SettlementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SettlementRepository
        extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findBySettlementId(String settlementId);

    Page<Settlement> findByMerchantId(
            String merchantId, Pageable pageable);

    Page<Settlement> findByMerchantIdAndStatus(
            String merchantId,
            SettlementStatus status,
            Pageable pageable);

    boolean existsByMerchantIdAndSettlementDateAndStatusIn(
            String merchantId,
            LocalDate settlementDate,
            java.util.Collection<SettlementStatus> statuses);
}
