package com.securepay.notification_service.service.serviceImpl;

import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.entity.Notification;
import com.securepay.notification_service.provider.EmailProvider;
import com.securepay.notification_service.provider.PushProvider;
import com.securepay.notification_service.provider.SmsProvider;
import com.securepay.notification_service.repository.NotificationRepository;
import com.securepay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final EmailProvider email;
    private final SmsProvider sms;
    private final PushProvider push;


    @Override
    public void sendNotification(NotificationEvent event) {
        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setType(event.getType());
        notification.setMessage(event.getMessage());
        notification.setStatus("SENT");
        repository.save(notification);
        switch (event.getType()) {
            case "EMAIL" -> email.sendEmail(event.getMessage());
            case "SMS" -> sms.sendSms(event.getMessage());
            case "PUSH" -> push.sendPush(event.getMessage());
        }
    }

    @Override
    public List<Notification> history(String userId) {
        return repository.findByUserId(userId);
    }
}
