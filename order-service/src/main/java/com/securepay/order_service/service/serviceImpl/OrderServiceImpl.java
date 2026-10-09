```java
package com.securepay.order_service.service.serviceImpl;

import com.securepay.order_service.component.OrderStateMachine;
import com.securepay.order_service.entity.Order;
import com.securepay.order_service.enums.OrderStatus;
import com.securepay.order_service.exception.OrderNotFoundException;
import com.securepay.order_service.repository.OrderRepository;
import com.securepay.order_service.request.CreateOrderRequest;
import com.securepay.order_service.response.OrderResponse;
import com.securepay.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderStateMachine stateMachine;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();

        order.setOrderId("ORD_" + UUID.randomUUID());
        order.setMerchantId(request.getMerchantId());
        order.setCustomerId(request.getCustomerId());
        order.setAmount(request.getAmount());
        order.setCurrency(
            request.getCurrency().toUpperCase(Locale.ROOT)
        );
        order.setDescription(request.getDescription());
        order.setStatus(OrderStatus.CREATED);

        Order saved = repository.save(order);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(String orderId) {

        Order order = repository.findByOrderId(orderId)
            .orElseThrow(
                () -> new OrderNotFoundException(orderId)
            );

        return toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse transitionStatus(
            String orderId,
            String nextStatus) {

        Order order = repository.findByOrderId(orderId)
            .orElseThrow(
                () -> new OrderNotFoundException(orderId)
            );

        OrderStatus next;

        try {
            next = OrderStatus.valueOf(
                nextStatus.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "Unsupported order status: " + nextStatus
            );
        }

        stateMachine.validate(order.getStatus(), next);

        order.setStatus(next);

        return toResponse(repository.save(order));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
            order.getOrderId(),
            order.getAmount(),
            order.getCurrency(),
            order.getStatus().name()
        );
    }
}
```
