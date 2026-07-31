package com.common.service;

import jakarta.annotation.PostConstruct;
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
public class MessageService {
    MessageSource messageSource;

    private static MessageService instance;

    @PostConstruct
    void init() {
        instance = this;
    }

    /**
     * Lấy ra thông điệp dựa trên key
     */
    public String get(String key, Object... args) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, key, locale);
    }

    /**
     * Lấy ra thông điệp dựa trên key từ static context (cho các lớp không thể inject trực tiếp như Interface Repository)
     */
    public static String getMessage(String key, Object... args) {
        if (instance == null) {
            return key;
        }
        return instance.get(key, args);
    }
}
