package com.common.config.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp cấu hình bảo mật SecurityFilterChain của Spring Security.<br/>
 * Thiết lập các bộ lọc xác thực JWT, cấu hình CORS, CSRF, Session,
 * và danh sách public URLs động từ configuration.<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @author txhoan
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
abstract class SecurityConfiguration {
    @Component
    @ConfigurationProperties(prefix = "cors")
    @Getter
    @Setter
    static class CorsProperties {
        private List<String> allowedOrigins;
    }

    JwtAuthenticationConverter converter;
    CorsProperties corsProperties;
    JwtDecoder decoder;
    SecurityProperties securityProperties;

    /**
     * Cấu hình chuỗi bộ lọc bảo mật SecurityFilterChain của Spring Security.<br/>
     * Khai báo phân quyền truy cập, CSRF, CORS, xử lý ngoại lệ và Keycloak JWT resource server.<br/>
     * Created at 10/06/2026, Dynamic URLs integrated at 19/06/2026
     *
     * @param http Đối tượng cấu hình HttpSecurity
     * @return Cấu hình SecurityFilterChain hoàn chỉnh
     * @throws Exception nếu xảy ra lỗi cấu hình
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Khởi tạo danh sách các URL công khai tĩnh (Swagger, Docs, Healthcheck)
        var allPublicUrls = new ArrayList<>(List.of(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/health"
        ));
        // Đọc thêm các URL công khai từ file cấu hình động (application.yaml)
        if (securityProperties.getPublicUrls() != null) {
            allPublicUrls.addAll(securityProperties.getPublicUrls());
        }

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allPublicUrls.toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("{\"error\": \"Authentication required\"}");
                        })
                        .accessDeniedHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write("{\"error\": \"Access denied\"}");
                        }))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(decoder)
                                .jwtAuthenticationConverter(converter))
                )
                .build();
    }

    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
