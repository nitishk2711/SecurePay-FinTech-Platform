package com.securepay.merchant_service.controller;

import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MerchantRequest request) {
        return ResponseEntity.ok(merchantService.onboard(request));
    }
}