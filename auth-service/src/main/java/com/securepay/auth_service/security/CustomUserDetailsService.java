package com.securepay.auth_service.security;

import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().getName()));

        user.getRole().getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(
                        permission.getName())));

        boolean enabled = user.getStatus()
                == com.securepay.auth_service.entity.UserStatus.ACTIVE
                && user.isEmailVerified();

        boolean accountNonLocked =
                user.getLockedUntil() == null
                || !user.getLockedUntil().isAfter(java.time.Instant.now());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!enabled)
                .accountLocked(!accountNonLocked)
                .build();
    }
}
