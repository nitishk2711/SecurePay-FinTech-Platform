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
                .route("auth-service", r ->
                        r.path("/api/auth/**").uri("lb://auth-service"))
                .route("merchant-service", r ->
                        r.path("/api/merchant/**").uri("lb://merchant-service"))
                .route("payment-service", r ->
                        r.path("/api/payment/**").uri("lb://payment-service"))
                .build();

    }

}
