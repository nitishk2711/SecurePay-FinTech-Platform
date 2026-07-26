package com.securepay.auth_service.service.impl;

import com.securepay.auth_service.dto.LoginRequest;
import com.securepay.auth_service.dto.LoginResponse;
import com.securepay.auth_service.dto.UpdateRoleRequest;
import com.securepay.auth_service.dto.UserRegisterRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.repository.RoleRepository;
import com.securepay.auth_service.repository.UserRepository;
import com.securepay.auth_service.security.JwtService;
import com.securepay.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;


    @Override
    public void register(UserRegisterRequest request) {

        Role role = roleRepository.findByName(request.getRoleName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

    }


    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }

    @Override
    public void updateRole(UpdateRoleRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void createRole(Role request) {

        roleRepository.findByName(request.getName())
                .ifPresent(role -> {
                    throw new RuntimeException("Role already exists");
                });

        Role role = new Role();
        role.setName(request.getName().toUpperCase());

        roleRepository.save(role);
    }


}