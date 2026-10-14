
package com.securepay.notification_service.provider;

import com.securepay.notification_service.dto.ProviderResponse;

public interface PushProvider {

    ProviderResponse sendPush(
            String deviceToken,
            String title,
            String message
    );
}
