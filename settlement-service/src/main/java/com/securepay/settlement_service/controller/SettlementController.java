package com.securepay.settlement_service.controller;

import com.securepay.settlement_service.dto.ApiResponseDto;
import com.securepay.settlement_service.dto.CreateSettlementRequest;
import com.securepay.settlement_service.dto.SettlementResponse;
import com.securepay.settlement_service.entity.Settlement;
import com.securepay.settlement_service.service.SettlementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<SettlementResponse>> createSettlement(
            @Valid @RequestBody CreateSettlementRequest request) {
        try {
            SettlementResponse response =
                    settlementService.createSettlement(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "Settlement created successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create settlement",
                            null
                    )
            );
        }
    }

    @PostMapping("/{settlementId}/process")
    public ResponseEntity<ApiResponseDto<SettlementResponse>> processSettlement(
            @PathVariable String settlementId) {
        try {
            SettlementResponse response =
                    settlementService.processSettlement(settlementId);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Settlement processed successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to process settlement",
                            null
                    )
            );
        }
    }

    @GetMapping("/{settlementId}")
    public ResponseEntity<ApiResponseDto<SettlementResponse>> getSettlement(
            @PathVariable String settlementId) {
        try {
            SettlementResponse response =
                    settlementService.getSettlement(settlementId);

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
                            "Failed to retrieve settlement",
                            null
                    )
            );
        }
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<ApiResponseDto<List<Settlement>>> getMerchantSettlements(
            @PathVariable String merchantId) {
        try {
            List<Settlement> response =
                    settlementService.getMerchantSettlements(merchantId);

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
                            "Failed to retrieve merchant settlements",
                            null
                    )
            );
        }
    }
}
