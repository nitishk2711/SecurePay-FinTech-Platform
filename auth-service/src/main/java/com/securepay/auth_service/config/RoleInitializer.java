package com.securepay.auth_service.config;

import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initializeRoles() {
        return args -> {
            List<String> roles = List.of(
                    "CUSTOMER",
                    "MERCHANT",
                    "SUPPORT",
                    "ADMIN"
            );

            for (String name : roles) {
                if (!roleRepository.existsByName(name)) {
                    Role role = new Role();
                    role.setName(name);
                    roleRepository.save(role);
                }
            }
        };
    }
}
