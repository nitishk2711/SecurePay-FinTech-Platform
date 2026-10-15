package com.securepay.notification_service.entity;

import com.securepay.notification_service.enums.NotificationPriority;
import com.securepay.notification_service.enums.NotificationStatus;
import com.securepay.notification_service.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Getter
@Setter
public class Notification {

    @Id
    private String id;

    @Indexed
    @Field("user_id")
    private String userId;

    @Field("event_id")
    private String eventId;

    private NotificationType type;

    @Indexed
    private NotificationStatus status;

    private NotificationPriority priority = NotificationPriority.NORMAL;

    private String recipient;

    private String subject;

    private String message;

    private String provider;

    private String providerMessageId;

    private int retryCount;

    private String failureReason;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime updatedAt;

    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        updatedAt = now;

        if (status == null) {
            status = NotificationStatus.CREATED;
        }

        if (priority == null) {
            priority = NotificationPriority.NORMAL;
        }
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}