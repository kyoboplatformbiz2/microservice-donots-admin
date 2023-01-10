package com.kyobo.platform.donots.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfig implements TransactionManagementConfigurer {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
    private String DB_NAME =  "/donots_account";

    @Bean
    public DataSource dataSource() {
        int forwardPort = new SSHConfig().init();
        String realUrl = url + ":" + forwardPort + DB_NAME;
        log.info("realUrl : " + realUrl);
        return DataSourceBuilder.create()
                .url(realUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManger();
    }

    @Bean
    public PlatformTransactionManager transactionManger() {
        return new DataSourceTransactionManager(dataSource());
    }
}
