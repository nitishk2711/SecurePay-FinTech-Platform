package com.securepay.payment_processor_service.response;


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
