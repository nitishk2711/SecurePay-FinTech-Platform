package com.securepay.notification_service.controller;

import com.securepay.notification_service.dto.ApiResponseDto;
import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.dto.NotificationResponse;
import com.securepay.notification_service.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<NotificationResponse>> sendNotification(
            @Valid @RequestBody NotificationEvent event) {
        try {
            NotificationResponse response =
                    notificationService.sendNotification(event);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.ACCEPTED.value(),
                            "Notification accepted successfully",
                            response
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to send notification",
                            null
                    )
            );
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponseDto<Page<NotificationResponse>>> history(
            @PathVariable String userId,
            Pageable pageable) {
        try {
            Page<NotificationResponse> response =
                    notificationService.history(userId, pageable);

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
                            "Failed to retrieve notification history",
                            null
                    )
            );
        }
    }
}
