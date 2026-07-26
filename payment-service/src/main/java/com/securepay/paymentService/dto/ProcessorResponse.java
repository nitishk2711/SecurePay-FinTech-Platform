package com.securepay.paymentService.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorResponse {

    private String paymentId;

    private String status;

    private String message;


}
