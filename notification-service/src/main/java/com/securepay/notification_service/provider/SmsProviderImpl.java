package com.securepay.notification_service.provider;

import com.securepay.notification_service.dto.ProviderResponse;
import org.springframework.stereotype.Component;

@Component
public class SmsProviderImpl implements SmsProvider {

    @Override
    public ProviderResponse sendSms(
            String phoneNumber,
            String message) {

        // TODO: Integrate your SMS gateway here.
        return ProviderResponse.builder()
                .success(false)
                .provider("SMS")
                .errorMessage("SMS gateway is not configured")
                .build();
    }
}