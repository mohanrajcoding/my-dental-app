package com.auth_service.security;

import com.auth_service.entity.AppUser;
import com.auth_service.exception.SystemException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private final String secretKey;
    private final long jwtExpirationMs;
    private Key key;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secretKey,
            @Value("${app.jwt.access-token-validity-seconds}") long jwtExpirationSeconds
    ) {
        this.secretKey = secretKey;
        this.jwtExpirationMs = jwtExpirationSeconds * 1000; // convert to milliseconds
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new SystemException("JWT token is invalid or expired");
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}