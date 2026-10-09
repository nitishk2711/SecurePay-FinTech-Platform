package com.securepay.auth_service.security;

import com.securepay.auth_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessTokenMinutes;
    private final String issuer;

    public JwtService(
            @Value("${securepay.jwt.secret}") String secret,
            @Value("${securepay.jwt.access-token-minutes:15}")
            long accessTokenMinutes,
            @Value("${securepay.jwt.issuer:securepay-auth}")
            String issuer) {

        byte[] decoded = java.util.Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decoded);
        this.accessTokenMinutes = accessTokenMinutes;
        this.issuer = issuer;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(accessTokenMinutes * 60);

        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("role", user.getRole().getName())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(parseToken(token).getSubject());
    }

    public long getAccessTokenSeconds() {
        return accessTokenMinutes * 60;
    }
}
