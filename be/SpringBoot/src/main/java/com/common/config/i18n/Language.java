package com.common.config.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum Language {
    VI("vi"),
    EN("en");

    private final String code;

    public Locale toLocale() {
        return Locale.forLanguageTag(code);
    }
}
