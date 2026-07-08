package com.securepay.order_service.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection="orders")
public class Order {
    @Id
    private String id;
    private Long orderId;
    private Long merchantId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
