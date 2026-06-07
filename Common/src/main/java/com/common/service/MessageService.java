package com.common.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Xử lý đa ngôn ngữ
 * Created at 07/06/2026
 * @author txhoan
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MessageService {
    MessageSource messageSource;

    /**
     * Lấy ra thông điệp dựa trên key
     */
    public String get(String key, Object... args) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }
}
