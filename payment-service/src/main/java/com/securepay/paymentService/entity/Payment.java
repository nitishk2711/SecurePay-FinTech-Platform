package com.securepay.paymentService.entity;

import com.securepay.paymentService.enums.PaymentMethod;
import com.securepay.paymentService.enums.PaymentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "payments")

public class Payment {
    @Id
    private String id;


    private String paymentId;
    private String orderId;
    private String merchantId;
    private String customerId;

    private BigDecimal amount;
    private String currency;

    //    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    //    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String processorRef;
    private Date createdAt;
    private Date updatedAt;
}
