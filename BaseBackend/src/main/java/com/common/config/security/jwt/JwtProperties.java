package com.common.config.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties cấu hình Security Provider & JWT từ application.yaml / .env.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class JwtProperties {

    /**
     * Auth Provider: "jwt" (Custom JWT) hoặc "keycloak" (OAuth2 Resource Server).
     */
    private String provider = "jwt";

    private JwtConfig jwt = new JwtConfig();

    @Data
    public static class JwtConfig {
        private boolean enabled = true;
        private String secretKey;
        private long expirationMs = 86400000L;
        private long refreshExpirationMs = 604800000L;
        private String issuer;
    }
}
