
package com.securepay.notification_service.provider;

import com.securepay.notification_service.dto.ProviderResponse;

public interface SmsProvider {

    ProviderResponse sendSms(
            String phoneNumber,
            String message
    );
}
