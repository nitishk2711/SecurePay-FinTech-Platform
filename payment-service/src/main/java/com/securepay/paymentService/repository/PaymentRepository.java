
package com.securepay.paymentService.repository;

import com.securepay.paymentService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, String> {

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

    List<Payment> findByMerchantIdOrderByCreatedAtDesc(
            String merchantId
    );

    List<Payment> findByOrderId(String orderId);
}
