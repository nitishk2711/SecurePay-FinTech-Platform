package com.securepay.notification_service.service;

import com.securepay.notification_service.provider.SmsProvider;
import org.springframework.stereotype.Service;

@Service
public class SmsProviderImpl implements SmsProvider {

    @Override
    public void sendSms(String message) {
        System.out.println("SMS SENT : " + message);
    }
}
