package com.securepay.auth_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.SecureRandom;

@Data
@Document(collection = "roles")
public class    Role {
    @Id
    private String id;
    private String name;
}
