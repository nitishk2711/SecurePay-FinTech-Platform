package com.securepay.notification_service.service;

import com.securepay.notification_service.provider.PushProvider;
import org.springframework.stereotype.Service;

@Service
public class PushProviderImpl implements PushProvider {

    @Override
    public void sendPush(String message) {
        System.out.println("PUSH SENT : " + message);
    }
}
