package com.common.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Factory nạp file cấu hình dạng YAML cho @PropertySource.<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        var factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        var properties = factory.getObject();
        if (properties == null) {
            return new PropertiesPropertySource(
                    (name != null ? name : resource.getResource().getFilename()),
                    new Properties()
            );
        }
        return new PropertiesPropertySource(
                (name != null ? name : resource.getResource().getFilename()),
                properties
        );
    }
}
