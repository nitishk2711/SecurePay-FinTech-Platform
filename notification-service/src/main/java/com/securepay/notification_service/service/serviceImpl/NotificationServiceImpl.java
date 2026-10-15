
package com.securepay.notification_service.service.serviceImpl;

import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.dto.NotificationResponse;
import com.securepay.notification_service.dto.ProviderResponse;
import com.securepay.notification_service.entity.Notification;
import com.securepay.notification_service.enums.NotificationStatus;
import com.securepay.notification_service.enums.NotificationType;
import com.securepay.notification_service.provider.EmailProvider;
import com.securepay.notification_service.provider.PushProvider;
import com.securepay.notification_service.provider.SmsProvider;
import com.securepay.notification_service.repository.NotificationRepository;
import com.securepay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;
    private final EmailProvider email;
    private final SmsProvider sms;
    private final PushProvider push;

    @Override
    @Transactional
    public NotificationResponse sendNotification(
            NotificationEvent event) {

        var existing = repository.findByEventId(event.getEventId());

        if (existing.isPresent()) {
            return toResponse(existing.get());
        }

        Notification notification = new Notification();
        notification.setEventId(event.getEventId());
        notification.setUserId(event.getUserId());
        notification.setType(event.getType());
        notification.setPriority(event.getPriority());
        notification.setRecipient(event.getRecipient());
        notification.setSubject(event.getSubject());
        notification.setMessage(event.getMessage());
        notification.setStatus(NotificationStatus.PROCESSING);

        // Persist before attempting external delivery.
        notification = repository.saveAndFlush(notification);

        try {
            ProviderResponse result = switch (event.getType()) {
                case EMAIL -> email.sendEmail(
                        event.getRecipient(),
                        event.getSubject(),
                        event.getMessage()
                );
                case SMS -> sms.sendSms(
                        event.getRecipient(),
                        event.getMessage()
                );
                case PUSH -> push.sendPush(
                        event.getRecipient(),
                        event.getSubject(),
                        event.getMessage()
                );
                case IN_APP -> ProviderResponse.builder()
                        .success(true)
                        .provider("IN_APP")
                        .build();
            };

            notification.setProvider(result.getProvider());
            notification.setProviderMessageId(
                    result.getProviderMessageId());

            if (result.isSuccess()) {
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
                notification.setFailureReason(null);
            } else {
                notification.setStatus(NotificationStatus.RETRYING);
                notification.setFailureReason(
                        result.getErrorMessage());
            }

        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.RETRYING);
            notification.setFailureReason(
                    "Provider delivery failed");
            // Log the exception securely in production.
        }

        Notification saved = repository.save(notification);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> history(
            String userId,
            Pageable pageable) {

        return repository
                .findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toResponse);
    }

    private NotificationResponse toResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .status(notification.getStatus())
                .priority(notification.getPriority())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .deliveredAt(notification.getDeliveredAt())
                .build();
    }
}
