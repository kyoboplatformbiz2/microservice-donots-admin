package com.kyobo.platform.donots.config;

import com.kyobo.platform.donots.config.session.SessionListener;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

public class WebConfig {
    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
}
