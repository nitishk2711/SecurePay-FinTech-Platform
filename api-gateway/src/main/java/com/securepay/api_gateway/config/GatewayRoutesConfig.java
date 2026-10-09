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
                // AUTH SERVICE API
                // =====================================================
                .route("auth-service", r ->
                        r.path("/auth-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://AUTH-SERVICE"))

                // =====================================================
                // MERCHANT SERVICE API
                // =====================================================
                .route("merchant-service", r ->
                        r.path("/api/merchant/**")
                                .uri("lb://SECURE-PAY-MERCHANT-SERVICE"))

                // =====================================================
                // PAYMENT SERVICE API
                // =====================================================
                .route("payment-service", r ->
                        r.path("/api/payment/**")
                                .uri("lb://PAYMENT-SERVICE"))

                // =====================================================
                // ACCOUNT SERVICE API
                // =====================================================
                .route("account-service", r ->
                        r.path("/account-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://ACCOUNT-SERVICE"))

                // =====================================================
                // PAYMENT PROCESSOR API
                // =====================================================
                .route("payment-processor-service", r ->
                        r.path("/payment-processor-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://PAYMENT-PROCESSOR-SERVICE"))

                // =====================================================
                // LEDGER SERVICE API
                // =====================================================
                .route("ledger-service", r ->
                        r.path("/ledger-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://LEDGER-SERVICE"))

                // =====================================================
                // ORDER SERVICE API
                // =====================================================
                .route("order-service", r ->
                        r.path("/order-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://ORDER-SERVICE"))

                // =====================================================
                // TRANSACTION SERVICE API
                // =====================================================
                .route("transaction-service", r ->
                        r.path("/transaction-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://TRANSACTION-SERVICE"))

                // =====================================================
                // SETTLEMENT SERVICE API
                // =====================================================
                .route("settlement-service", r ->
                        r.path("/settlement-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://SETTLEMENT-SERVICE"))

                // =====================================================
                // FRAUD SERVICE API
                // =====================================================
                .route("fraud-service", r ->
                        r.path("/fraud-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://FRAUD-SERVICE"))

                // =====================================================
                // NOTIFICATION SERVICE API
                // =====================================================
                .route("notification-service", r ->
                        r.path("/notification-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://NOTIFICATION-SERVICE"))

                // =====================================================
                // AUDIT SERVICE API
                // =====================================================
                .route("audit-service", r ->
                        r.path("/audit-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://AUDIT-SERVICE"))

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
                                .uri("lb://AUTH-SERVICE"))

                // =====================================================
                // ACCOUNT SERVICE SWAGGER
                // =====================================================
                .route("account-service-swagger", r ->
                        r.path("/account-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://ACCOUNT-SERVICE"))

                // =====================================================
                // PAYMENT PROCESSOR SWAGGER
                // =====================================================
                .route("payment-processor-service-swagger", r ->
                        r.path("/payment-processor-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://PAYMENT-PROCESSOR-SERVICE"))

                // =====================================================
                // LEDGER SWAGGER
                // =====================================================
                .route("ledger-service-swagger", r ->
                        r.path("/ledger-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://LEDGER-SERVICE"))

                // =====================================================
                // ORDER SWAGGER
                // =====================================================
                .route("order-service-swagger", r ->
                        r.path("/order-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://ORDER-SERVICE"))

                // =====================================================
                // PAYMENT SWAGGER
                // =====================================================
                .route("payment-service-swagger", r ->
                        r.path("/payment-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://PAYMENT-SERVICE"))

                // =====================================================
                // TRANSACTION SWAGGER
                // =====================================================
                .route("transaction-service-swagger", r ->
                        r.path("/transaction-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://TRANSACTION-SERVICE"))

                // =====================================================
                // SETTLEMENT SWAGGER
                // =====================================================
                .route("settlement-service-swagger", r ->
                        r.path("/settlement-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://SETTLEMENT-SERVICE"))

                // =====================================================
                // FRAUD SWAGGER
                // =====================================================
                .route("fraud-service-swagger", r ->
                        r.path("/fraud-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://FRAUD-SERVICE"))

                // =====================================================
                // NOTIFICATION SWAGGER
                // =====================================================
                .route("notification-service-swagger", r ->
                        r.path("/notification-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://NOTIFICATION-SERVICE"))

                // =====================================================
                // AUDIT SWAGGER
                // =====================================================
                .route("audit-service-swagger", r ->
                        r.path("/audit-service/v3/api-docs")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://AUDIT-SERVICE"))

                .build();
    }
}