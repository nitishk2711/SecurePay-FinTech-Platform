```java
package com.securepay.payment_processor_service.controller;

import com.securepay.payment_processor_service.dto.ApiResponseDto;
import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;
import com.securepay.payment_processor_service.service.ProcessorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/processor")
@RequiredArgsConstructor
public class ProcessorController {

    private final ProcessorService processorService;

    @PostMapping("/payments")
    public ResponseEntity<ApiResponseDto<ProcessorResponse>> processPayment(
            @Valid @RequestBody ProcessorRequest request) {
        try {
            ProcessorResponse response =
                    processorService.processPayment(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Payment processed successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Payment processing failed",
                            null
                    )
            );
        }
    }
}
```
