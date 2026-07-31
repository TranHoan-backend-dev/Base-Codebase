package com.common.config.i18n;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Properties;

/**
 * Cấu hình MessageSource hỗ trợ nạp tệp đa ngôn ngữ dạng YAML (.yaml / .yml).<br/>
 *
 * @created_at 2026-07-28
 * @author txhoan
 */
@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource() {
            @Override
            protected Properties loadProperties(Resource resource, String filename) throws java.io.IOException {
                String resFilename = resource.getFilename();
                if (resource.exists() && resFilename != null && (resFilename.endsWith(".yaml") || resFilename.endsWith(".yml"))) {
                    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
                    factory.setResources(resource);
                    Properties props = factory.getObject();
                    return props != null ? props : new Properties();
                }
                return super.loadProperties(resource, filename);
            }
        };
        messageSource.setBasenames("classpath:messages", "classpath:ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFileExtensions(List.of(".yaml", ".yml"));
        return messageSource;
    }
}
