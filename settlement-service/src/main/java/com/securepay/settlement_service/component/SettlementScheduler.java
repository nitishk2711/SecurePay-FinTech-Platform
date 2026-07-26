package com.securepay.settlement_service.component;

import com.securepay.settlement_service.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlementScheduler {

    private final SettlementService service;

    @Scheduled(cron = "0 0 0 * * *")
    public void runSettlement() {
        System.out.println("Daily Settlement Started");
    }
}
