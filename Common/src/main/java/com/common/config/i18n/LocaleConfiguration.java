package com.common.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
class LocaleConfiguration {
    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("vi"));
        resolver.setSupportedLocales(
                List.of(
                        Language.EN.toLocale(),
                        Language.VI.toLocale()
                )
        );
        return resolver;
    }
}
