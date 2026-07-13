package com.securepay.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //Framework creates object(Spring/Jackson)
@AllArgsConstructor //Your code creates object
public class JwtResponse {
    private String token;
}
