package com.securepay.paymentService.dto;


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
