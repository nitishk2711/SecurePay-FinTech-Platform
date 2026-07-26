package com.securepay.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {

    private String userId;

    private String type;

    private String message;
}
