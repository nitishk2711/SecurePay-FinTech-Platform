
package com.securepay.payment_processor_service.repository;

import com.securepay.payment_processor_service.entity.ProcessorTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcessorTransactionRepository
        extends JpaRepository<ProcessorTransaction, UUID> {

    Optional<ProcessorTransaction> findByProcessorNameAndIdempotencyKey(
            String processorName,
            String idempotencyKey
    );

    Optional<ProcessorTransaction> findByIdAndPaymentId(
            UUID id,
            UUID paymentId
    );
}
