package com.securepay.notification_service.consumer;

import com.securepay.notification_service.dto.NotificationEvent;
import com.securepay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final NotificationService service;

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consume(String event) {
        NotificationEvent notification = new NotificationEvent();
        notification.setUserId("CUSTOMER");
        notification.setType("EMAIL");
        notification.setMessage("Payment Completed Successfully");
        service.sendNotification(notification);
    }
}
