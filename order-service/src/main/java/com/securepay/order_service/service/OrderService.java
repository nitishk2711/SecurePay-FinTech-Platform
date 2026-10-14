package com.securepay.order_service.service;

import com.securepay.order_service.request.CreateOrderRequest;
import com.securepay.order_service.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrder(String orderId);

    OrderResponse transitionStatus(
        String orderId,
        String nextStatus
    );
}
