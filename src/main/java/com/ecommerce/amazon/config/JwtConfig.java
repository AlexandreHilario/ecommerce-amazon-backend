package com.ecommerce.amazon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24; // 24h

    public String getSecret() {
        return secret;
    }

    public long getExpirationMs() {
        return EXPIRATION_MS;
    }
}
