
package com.securepay.notification_service.service;

import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.dto.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponse sendNotification(
            NotificationEvent event
    );

    Page<NotificationResponse> history(
            String userId,
            Pageable pageable
    );
}
