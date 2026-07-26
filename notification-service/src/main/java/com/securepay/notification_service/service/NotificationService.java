package com.securepay.notification_service.service;

import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.entity.Notification;

import java.util.List;

public interface NotificationService {

    void sendNotification(NotificationEvent event);

    List<Notification> history(String userId);
}
