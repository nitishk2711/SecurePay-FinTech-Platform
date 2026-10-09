```java
package com.securepay.order_service.controller;

import com.securepay.order_service.request.CreateOrderRequest;
import com.securepay.order_service.response.OrderResponse;
import com.securepay.order_service.service.OrderService;
import com.securepay.order_service.dto.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        try {
            OrderResponse response = orderService.createOrder(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "Order created successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create order",
                            null
                    )
            );
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponse>> getOrder(
            @PathVariable String orderId) {
        try {
            OrderResponse response = orderService.getOrder(orderId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve order",
                            null
                    )
            );
        }
    }
}
```
