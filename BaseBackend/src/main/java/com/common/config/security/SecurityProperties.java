package com.common.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Cấu hình các thuộc tính bảo mật động từ file application.yaml.<br/>
 * Map tới prefix "security".<br/>
 * Created at 19/06/2026
 *
 * @author txhoan
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> publicUrls = new ArrayList<>();
}
