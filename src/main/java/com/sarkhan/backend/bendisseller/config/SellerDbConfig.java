package com.sarkhan.backend.bendisseller.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.sarkhan.backend.bendisseller.repository.seller",
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager"
)
public class SellerDbConfig {

    @Value("${spring.datasource.first.url}")
    private String firstDbUrl;

    @Value("${spring.datasource.first.username}")
    private String firstDbUsername;

    @Value("${spring.datasource.first.password}")
    private String firstDbPassword;

    @Bean(name = "firstDataSource", destroyMethod = "close")
    @Primary
    public HikariDataSource firstDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(firstDbUrl);
        dataSource.setUsername(firstDbUsername);
        dataSource.setPassword(firstDbPassword);
        dataSource.setPoolName("SellerDbHikariPool");
        return dataSource;
    }

    @Bean(name = "firstEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("firstDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sarkhan.backend.bendisseller.model.user")
                .persistenceUnit("first")
                .build();
    }

    @Bean(name = "firstTransactionManager")
    @Primary
    public PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstEntityManagerFactory") EntityManagerFactory firstEntityManagerFactory) {
        return new JpaTransactionManager(firstEntityManagerFactory);
    }
}
