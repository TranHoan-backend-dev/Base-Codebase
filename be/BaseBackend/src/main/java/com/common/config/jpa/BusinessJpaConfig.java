package com.common.config.jpa;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Cấu hình JPA cho datasource nghiệp vụ (Business).<br/>
 * Đây là datasource {@code @Primary} — các entity nghiệp vụ kế thừa {@code BaseModel}
 * từ các service cụ thể sẽ được scan và persist qua datasource này.<br/>
 * <p>
 * Package scan mặc định: {@code com.**.model.business} — mỗi service cần đặt
 * entity nghiệp vụ vào package {@code model.business} để được tự động quản lý.<br/>
 * <p>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.**.repository.business",
        entityManagerFactoryRef = "businessEntityManagerFactory",
        transactionManagerRef = "businessTransactionManager"
)
class BusinessJpaConfig {

    /**
     * DataSourceProperties cho business datasource từ {@code spring.datasource.business.*}.
     */
    @Primary
    @Bean("businessDataSourceProperties")
    @ConfigurationProperties("spring.datasource.business")
    public DataSourceProperties businessDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * HikariCP DataSource cho business datasource.
     * Được đánh dấu {@code @Primary} để Spring Boot tự động inject khi không có qualifier.
     */
    @Primary
    @Bean("businessDataSource")
    @ConfigurationProperties("spring.datasource.business.hikari")
    public HikariDataSource businessDataSource(
            @Qualifier("businessDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * EntityManagerFactory cho business datasource.<br/>
     * Scan các entity trong package {@code com.**.model.business}.
     */
    @Primary
    @Bean("businessEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean businessEntityManagerFactory(
            @Qualifier("businessDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.*.model.business");
        em.setPersistenceUnitName("business");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(Map.of(
                "hibernate.hbm2ddl.auto", "update",
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                "hibernate.format_sql", "true",
                "hibernate.show_sql", "false"
        ));
        return em;
    }

    /**
     * TransactionManager cho business datasource.
     */
    @Primary
    @Bean("businessTransactionManager")
    public PlatformTransactionManager businessTransactionManager(
            @Qualifier("businessEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
