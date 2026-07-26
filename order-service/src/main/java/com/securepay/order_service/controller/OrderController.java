package com.securepay.order_service.controller;

import com.securepay.order_service.request.CreateOrderRequest;
import com.securepay.order_service.response.OrderResponse;
import com.securepay.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> get(@PathVariable String orderId) {
        return ResponseEntity.ok(service.getOrder(orderId));
    }
}