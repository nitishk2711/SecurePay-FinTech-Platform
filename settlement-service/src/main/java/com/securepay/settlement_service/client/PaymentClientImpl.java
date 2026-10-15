package com.securepay.settlement_service.client;

import com.securepay.settlement_service.dto.EligiblePayment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@Component
public class PaymentClientImpl implements PaymentClient {

    private final RestClient restClient;

    public PaymentClientImpl(
            RestClient.Builder builder,
            @Value("${payment.service.url}") String paymentServiceUrl) {

        this.restClient = builder
                .baseUrl(paymentServiceUrl)
                .build();
    }

    @Override
    public List<EligiblePayment> getEligiblePayments(
            String merchantId,
            LocalDate periodStart,
            LocalDate periodEnd,
            String currency) {

        List<EligiblePayment> payments = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/payments/eligible")
                        .queryParam("merchantId", merchantId)
                        .queryParam("periodStart", periodStart)
                        .queryParam("periodEnd", periodEnd)
                        .queryParam("currency", currency)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<EligiblePayment>>() {});

        return payments != null ? payments : List.of();
    }
}