package com.securepay.api_gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                // =====================================================
                // AUTH SERVICE
                // =====================================================
                .route("auth-service", r ->
                        r.path("/api/auth/**")
                                .uri("lb://auth-service"))

                // =====================================================
                // MERCHANT SERVICE
                // =====================================================
                .route("merchant-service", r ->
                        r.path("/api/merchant/**")
                                .uri("lb://SECURE-PAY-MERCHANT-SERVICE"))

                // =====================================================
                // PAYMENT SERVICE
                // =====================================================
                .route("payment-service", r ->
                        r.path("/api/payment/**")
                                .uri("lb://payment-service"))

                // =====================================================
                // ACCOUNT SERVICE SWAGGER
                // =====================================================
                .route("account-service-swagger", r ->
                        r.path("/account-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://account-service"))

                // =====================================================
                // PAYMENT PROCESSOR SERVICE SWAGGER
                // =====================================================
                .route("payment-processor-service-swagger", r ->
                        r.path("/payment-processor-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://payment-processor-service"))

                // =====================================================
                // LEDGER SERVICE SWAGGER
                // =====================================================
                .route("ledger-service-swagger", r ->
                        r.path("/ledger-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://ledger-service"))

                // =====================================================
                // ORDER SERVICE SWAGGER
                // =====================================================
                .route("order-service-swagger", r ->
                        r.path("/order-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://order-service"))

                // =====================================================
                // PAYMENT SERVICE SWAGGER
                // =====================================================
                .route("payment-service-swagger", r ->
                        r.path("/payment-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://payment-service"))

                // =====================================================
                // TRANSACTION SERVICE SWAGGER
                // =====================================================
                .route("transaction-service-swagger", r ->
                        r.path("/transaction-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://transaction-service"))

                // =====================================================
                // SETTLEMENT SERVICE SWAGGER
                // =====================================================
                .route("settlement-service-swagger", r ->
                        r.path("/settlement-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://settlement-service"))

                // =====================================================
                // FRAUD SERVICE SWAGGER
                // =====================================================
                .route("fraud-service-swagger", r ->
                        r.path("/fraud-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://fraud-service"))

                // =====================================================
                // NOTIFICATION SERVICE SWAGGER
                // =====================================================
                .route("notification-service-swagger", r ->
                        r.path("/notification-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://notification-service"))

                // =====================================================
                // AUDIT SERVICE SWAGGER
                // =====================================================
                .route("audit-service-swagger", r ->
                        r.path("/audit-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://audit-service"))

                // =====================================================
                // MERCHANT SERVICE SWAGGER
                // =====================================================
                .route("merchant-service-swagger", r ->
                        r.path("/secure-pay-merchant-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://SECURE-PAY-MERCHANT-SERVICE"))
                // =====================================================
                // AUTH SERVICE SWAGGER
                // =====================================================
                .route("auth-service-swagger", r ->
                        r.path("/auth-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://auth-service"))

                .build();
    }

}
