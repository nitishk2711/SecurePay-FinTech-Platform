
package com.securepay.paymentService.repository;

import com.securepay.paymentService.entity.PaymentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentAttemptRepository
        extends JpaRepository<PaymentAttempt, Long> {

    List<PaymentAttempt> findByPayment_PaymentIdOrderByAttemptNumberAsc(
            String paymentId
    );

    boolean existsByPayment_PaymentIdAndAttemptNumber(
            String paymentId,
            Integer attemptNumber
    );
}
