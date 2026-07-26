package com.securepay.payment_processor_service.request;


import lombok.*;


import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorRequest {

    private String paymentId;

    private BigDecimal amount;

    private String method;


}
