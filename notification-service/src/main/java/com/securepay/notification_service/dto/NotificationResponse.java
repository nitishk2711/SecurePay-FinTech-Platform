
package com.securepay.notification_service.dto;

import com.securepay.notification_service.enums.NotificationPriority;
import com.securepay.notification_service.enums.NotificationStatus;
import com.securepay.notification_service.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {

    private String id;
    private String userId;
    private NotificationType type;
    private NotificationStatus status;
    private NotificationPriority priority;
    private String subject;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
}
