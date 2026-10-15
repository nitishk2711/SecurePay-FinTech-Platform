
package com.securepay.notification_service.entity;

import com.securepay.notification_service.enums.NotificationPriority;
import com.securepay.notification_service.enums.NotificationStatus;
import com.securepay.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "notifications",
    indexes = {
        @Index(name = "idx_notification_user", columnList = "user_id"),
        @Index(name = "idx_notification_status", columnList = "status")
    }
)
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "event_id", length = 150)
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationPriority priority = NotificationPriority.NORMAL;

    @Column(length = 255)
    private String recipient;

    @Column(length = 200)
    private String subject;

    @Column(nullable = false, length = 5000)
    private String message;

    @Column(length = 100)
    private String provider;

    @Column(length = 200)
    private String providerMessageId;

    private int retryCount;

    @Column(length = 1000)
    private String failureReason;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;

        if (status == null) {
            status = NotificationStatus.CREATED;
        }

        if (priority == null) {
            priority = NotificationPriority.NORMAL;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
