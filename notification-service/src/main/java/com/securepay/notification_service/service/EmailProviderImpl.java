package com.securepay.notification_service.service;

import com.securepay.notification_service.provider.EmailProvider;
import org.springframework.stereotype.Service;

@Service
public class EmailProviderImpl implements EmailProvider {

    @Override
    public void sendEmail(String message) {
        System.out.println("EMAIL SENT : " + message);
    }

}
