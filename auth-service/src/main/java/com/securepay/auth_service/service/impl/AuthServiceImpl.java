package com.securepay.auth_service.service.impl;

import com.securepay.auth_service.dto.JwtResponse;
import com.securepay.auth_service.dto.LoginRequest;
import com.securepay.auth_service.dto.UserRegisterRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.exception.ResourceNotFoundException;
import com.securepay.auth_service.repository.RoleRepository;
import com.securepay.auth_service.repository.UserRepository;
import com.securepay.auth_service.security.JwtUtil;
import com.securepay.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User register(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        String token = jwtUtil.generateToken(user.getEmail(), role.getName());
        return new JwtResponse(token);
    }


    @Override
    public Role createRoles(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleId(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public Role updateRole(String id, Role role) {
        Role updaterole = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with Role id: " + id));
        updaterole.setId(role.getId());
        updaterole.setName(role.getName());
        return roleRepository.save(updaterole);
    }

    @Override
    public String deleteRole(String id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with role id:" + id);
        }
        roleRepository.deleteById(id);
        return "Role with id:"+id +"is deleted";
    }

    @Override
    public User updateRoleId(String userId,String roleId){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        user.setRoleId(roleId);
        return userRepository.save(user);
    }
}
//post
//user register
//login->generate Token




