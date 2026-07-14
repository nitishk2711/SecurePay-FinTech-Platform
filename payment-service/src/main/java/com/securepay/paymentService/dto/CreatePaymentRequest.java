package com.securepay.paymentService.dto;

import com.securepay.paymentService.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {
    private String orderId;
    private String merchantId;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
}
