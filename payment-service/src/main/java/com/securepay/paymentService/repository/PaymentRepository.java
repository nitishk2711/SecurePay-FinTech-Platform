package com.securepay.paymentService.repository;

import com.securepay.paymentService.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByOrderId(String orderId);

    Payment findByPayment_id(String paymentId);
}
