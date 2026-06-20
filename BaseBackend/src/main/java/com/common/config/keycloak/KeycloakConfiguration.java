package com.common.config.keycloak;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class KeycloakConfiguration {
    KeycloakProperties keycloakProperties;

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(keycloakProperties.getIssuerUri());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(keycloakProperties.getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(keycloakProperties.getAud());
        OAuth2TokenValidator<Jwt> withAzp = new AuthorizedPartyValidator(keycloakProperties.getClientId());

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                withIssuer,
                withAudience,
                withAzp));
        return decoder;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Set<String> allRoles = new HashSet<>();

            // 1. Extract Realm Roles
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            log.info("realmAccess: {}", realmAccess);
            if (realmAccess != null && realmAccess.get("roles") instanceof List<?> roles) {
                roles.forEach(role -> allRoles.add(role.toString()));
            }

            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null) {
                resourceAccess.values().forEach(resource -> {
                    if (resource instanceof Map<?, ?> map && map.get("roles") instanceof List<?> roles) {
                        roles.forEach(role -> allRoles.add(role.toString()));
                    }
                });
            }

            log.info("Extracted authorities for user {}: {}", jwt.getSubject(), allRoles);

            return allRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .collect(Collectors.toList());
        });
        return authenticationConverter;
    }
}
