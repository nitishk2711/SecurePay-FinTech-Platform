
package com.securepay.notification_service.provider;

import com.securepay.notification_service.dto.ProviderResponse;

public interface EmailProvider {

    ProviderResponse sendEmail(
            String recipient,
            String subject,
            String message
    );
}
