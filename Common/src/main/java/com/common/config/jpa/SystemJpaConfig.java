package com.common.config.jpa;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Cấu hình JPA cho datasource kỹ thuật (System).<br/>
 * Datasource này quản lý các entity hạ tầng/kỹ thuật dùng chung:
 * {@code OutboxEvent}, {@code SystemSetting}, {@code DynamicGridConfig}.<br/>
 * <p>
 * Khi sử dụng {@code @Transactional} trong service liên quan đến các entity này,
 * cần chỉ định qualifier: {@code @Transactional("systemTransactionManager")}.<br/>
 * <p>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.common.repository.sql",
        entityManagerFactoryRef = "systemEntityManagerFactory",
        transactionManagerRef = "systemTransactionManager"
)
class SystemJpaConfig {

    /**
     * DataSourceProperties cho system datasource từ {@code spring.datasource.system.*}.
     */
    @Bean("systemDataSourceProperties")
    @ConfigurationProperties("spring.datasource.system")
    public DataSourceProperties systemDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * HikariCP DataSource cho system datasource.
     */
    @Bean("systemDataSource")
    @ConfigurationProperties("spring.datasource.system.hikari")
    public HikariDataSource systemDataSource(
            @Qualifier("systemDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * EntityManagerFactory cho system datasource.<br/>
     * Scan các entity trong package {@code com.common.model.sql}:
     * {@code OutboxEvent}, {@code SystemSetting}, {@code DynamicGridConfig}, v.v.
     */
    @Bean("systemEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean systemEntityManagerFactory(
            @Qualifier("systemDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.common.model.sql");
        em.setPersistenceUnitName("system");

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
     * TransactionManager cho system datasource.<br/>
     * Sử dụng qualifier {@code "systemTransactionManager"} khi khai báo {@code @Transactional}.
     */
    @Bean("systemTransactionManager")
    public PlatformTransactionManager systemTransactionManager(
            @Qualifier("systemEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
