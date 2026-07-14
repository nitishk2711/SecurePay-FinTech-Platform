package com.securepay.paymentService.dto;

import com.securepay.paymentService.enums.PaymentMethod;
import com.securepay.paymentService.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String processorReference;
    private PaymentMethod paymentMethod;
}
