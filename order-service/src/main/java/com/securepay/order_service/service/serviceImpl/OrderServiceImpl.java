package com.securepay.order_service.service.serviceImpl;

import com.securepay.order_service.entity.Order;
import com.securepay.order_service.enums.OrderStatus;
import com.securepay.order_service.repository.OrderRepository;
import com.securepay.order_service.request.CreateOrderRequest;
import com.securepay.order_service.response.OrderResponse;
import com.securepay.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setOrderId(
                "ORD_" + UUID.randomUUID().toString().substring(0, 8)
        );

        order.setMerchantId(request.getMerchantId());
        order.setCustomerId(request.getCustomerId());
        order.setAmount(request.getAmount());
        order.setCurrency(request.getCurrency());
        order.setStatus(OrderStatus.CREATED.name());
        repository.save(order);

        return new OrderResponse(
                order.getOrderId(),
                order.getAmount(),
                order.getCurrency(),
                order.getStatus()
        );
    }


    @Override
    public OrderResponse getOrder(String orderId) {

        Order order = repository.findByOrderId(orderId).orElseThrow();

        return new OrderResponse(
                order.getOrderId(),
                order.getAmount(),
                order.getCurrency(),
                order.getStatus()
        );
    }


    @Override
    public void updateStatus(String orderId, String status) {

        Order order = repository.findByOrderId(orderId).orElseThrow();
        order.setStatus(status);
        repository.save(order);

    }
}