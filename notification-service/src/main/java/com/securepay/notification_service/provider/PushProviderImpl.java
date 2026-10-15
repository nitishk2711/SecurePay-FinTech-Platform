package com.securepay.notification_service.provider;

import com.securepay.notification_service.dto.ProviderResponse;
import org.springframework.stereotype.Component;

@Component
public class PushProviderImpl implements PushProvider {

    @Override
    public ProviderResponse sendPush(
            String recipient,
            String subject,
            String message) {

        // Replace this placeholder with your actual push
        // notification gateway integration.
        return ProviderResponse.builder()
                .success(false)
                .provider("PUSH")
                .errorMessage("Push provider is not configured")
                .build();
    }
}