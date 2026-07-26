package com.securepay.paymentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {

    private int success;
    private String message;
    private T data;

}
