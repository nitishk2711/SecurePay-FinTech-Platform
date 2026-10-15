
package com.securepay.notification_service.service;

import com.securepay.notification_service.dto.ProviderResponse;
import com.securepay.notification_service.provider.EmailProvider;
import org.springframework.stereotype.Service;

@Service
public class EmailProviderImpl implements EmailProvider {

    @Override
    public ProviderResponse sendEmail(
            String recipient,
            String subject,
            String message) {

        // Temporary mock provider for development.
        System.out.println(
                "MOCK EMAIL to " + recipient
                        + ", subject: " + subject
        );

        return ProviderResponse.builder()
                .success(true)
                .provider("MOCK_EMAIL")
                .providerMessageId(null)
                .build();
    }
}
