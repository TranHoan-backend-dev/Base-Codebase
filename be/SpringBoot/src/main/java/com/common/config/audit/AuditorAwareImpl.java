package com.common.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * Lấy thông tin người chỉnh sửa dữ liệu.<br/>
     * Create at 06/04/2026
     *
     * @return Tên người chỉnh sửa
     * @author txhoan
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        return Optional.of(authentication.getName());
    }
}
