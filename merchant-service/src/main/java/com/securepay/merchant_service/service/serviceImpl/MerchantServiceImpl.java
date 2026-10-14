
package com.securepay.merchant_service.controller;

import com.securepay.merchant_service.dto.ApiResponseDto;
import com.securepay.merchant_service.enums.MerchantStatus;
import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.request.UpdateMerchantRequest;
import com.securepay.merchant_service.response.MerchantResponse;
import com.securepay.merchant_service.service.MerchantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<MerchantResponse>> onboard(
            @Valid @RequestBody MerchantRequest request) {
        try {
            MerchantResponse response = merchantService.onboard(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "Merchant onboarded successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to onboard merchant",
                            null
                    )
            );
        }
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<ApiResponseDto<MerchantResponse>> getMerchant(
            @PathVariable String merchantId) {
        try {
            MerchantResponse response =
                    merchantService.getMerchant(merchantId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Success",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to retrieve merchant",
                            null
                    )
            );
        }
    }

    @PutMapping("/{merchantId}")
    public ResponseEntity<ApiResponseDto<MerchantResponse>> updateMerchant(
            @PathVariable String merchantId,
            @Valid @RequestBody UpdateMerchantRequest request) {
        try {
            MerchantResponse response =
                    merchantService.updateMerchant(merchantId, request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Merchant updated successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to update merchant",
                            null
                    )
            );
        }
    }

    @PatchMapping("/{merchantId}/status")
    public ResponseEntity<ApiResponseDto<MerchantResponse>> updateStatus(
            @PathVariable String merchantId,
            @RequestParam MerchantStatus status) {
        try {
            MerchantResponse response =
                    merchantService.updateStatus(merchantId, status);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Merchant status updated successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to update merchant status",
                            null
                    )
            );
        }
    }

    @PostMapping("/{merchantId}/keys/rotate")
    public ResponseEntity<ApiResponseDto<Void>> rotateApiKey(
            @PathVariable String merchantId) {
        try {
            merchantService.rotateApiKey(merchantId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Merchant API key rotated successfully",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to rotate merchant API key",
                            null
                    )
            );
        }
    }
}

