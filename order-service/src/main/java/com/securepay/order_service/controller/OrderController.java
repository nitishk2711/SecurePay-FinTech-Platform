package com.securepay.order_service.controller;

import com.securepay.order_service.dto.ApiResponseDto;
import com.securepay.order_service.entity.Order;
import com.securepay.order_service.exception.UnauthorizedException;
import com.securepay.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createorders")
    public Order createOrders(@RequestBody Order order) {
        return orderService.createOrders(order);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Order>> getOrderById(@PathVariable String id) {

        try {
            Optional<Order> orderOpt = orderService.getOrderById(id);

            if (orderOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDto<>(
                                404,
                                "Order not found",
                                null
                        ));
            }

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            200,
                            "success",
                            orderOpt.get()
                    )
            );

        } catch (UnauthorizedException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDto<>(
                            401,
                            "Unauthorized",
                            null
                    ));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(
                            500,
                            "Internal Server Error",
                            null
                    ));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
