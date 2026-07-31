package com.common.config.security.jwt;

import com.common.model.enumerate.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility Provider cho việc sinh, mã hóa và kiểm tra JWT Token (JJWT 0.12.6).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final TokenBlacklistService tokenBlacklistService;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getJwt().getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Sinh Access Token chứa Multitenancy claims.
     */
    public String generateAccessToken(Long userId, String username, String email, Long tenantId, String tenantCode, Set<UserRole> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        claims.put("user_id", userId);
        claims.put("email", email);
        claims.put("tenant_id", tenantId);
        claims.put("tenant_code", tenantCode);
        claims.put("roles", roles.stream().map(UserRole::name).collect(Collectors.toSet()));

        return buildToken(claims, username, jwtProperties.getJwt().getExpirationMs());
    }

    /**
     * Sinh Refresh Token.
     */
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        String refreshToken = buildToken(claims, username, jwtProperties.getJwt().getRefreshExpirationMs());
        tokenBlacklistService.storeRefreshToken(username, refreshToken, jwtProperties.getJwt().getRefreshExpirationMs());
        return refreshToken;
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuer(jwtProperties.getJwt().getIssuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("user_id", Long.class));
    }

    public Long extractTenantId(String token) {
        return extractClaim(token, claims -> claims.get("tenant_id", Long.class));
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            if (tokenBlacklistService.isBlacklisted(token)) {
                log.warn("Token is blacklisted");
                return false;
            }
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }

    public long getRemainingTtlMs(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0L;
        }
    }
}
