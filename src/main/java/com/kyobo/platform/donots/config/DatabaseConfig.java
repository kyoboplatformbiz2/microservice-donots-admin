package com.kyobo.platform.donots.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.ExceptionTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableJpaAuditing
@Configuration
@Slf4j
public class DatabaseConfig  {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private String DB_NAME =  "/postgres";

    @Bean
    public DataSource dataSource() {
        int forwardPort = 5432;
        if(!myIpCheck())
            forwardPort = new SSHConfig().init();
        String realUrl = url + ":" + forwardPort + DB_NAME;
        log.info("realUrl : " + realUrl);
        return DataSourceBuilder.create()
                .url(realUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }


    public boolean myIpCheck() {
        boolean result = false;
        String strHostName = null;

        try {
            InetAddress addr = InetAddress.getLocalHost();
            strHostName = addr.getHostName();
            log.info("Host name :" + strHostName);
        } catch (Exception e) {
            e.getMessage();
        }

        if (strHostName.equals("wlfek")) {
            result = true;
        }

        return result;
    }
}
