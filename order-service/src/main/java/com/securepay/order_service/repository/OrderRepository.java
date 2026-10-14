package com.securepay.order_service.repository;

import com.securepay.order_service.entity.Order;
import com.securepay.order_service.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    Optional<Order> findByOrderIdAndMerchantId(
        String orderId,
        String merchantId
    );

    Page<Order> findByMerchantId(
        String merchantId,
        Pageable pageable
    );

    Page<Order> findByMerchantIdAndStatus(
        String merchantId,
        OrderStatus status,
        Pageable pageable
    );
}
