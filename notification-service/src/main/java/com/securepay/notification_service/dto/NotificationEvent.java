
package com.securepay.notification_service.dto;

import com.securepay.notification_service.enums.NotificationPriority;
import com.securepay.notification_service.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {

    @NotBlank
    private String eventId;

    @NotBlank
    private String userId;

    @NotNull
    private NotificationType type;

    @NotBlank
    @Size(max = 5000)
    private String message;

    @Size(max = 255)
    private String recipient;

    @Size(max = 200)
    private String subject;

    private NotificationPriority priority =
            NotificationPriority.NORMAL;
}
