package com.kyobo.platform.donots.config;

import com.kyobo.platform.donots.service.LoginService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginService loginService;

    public SecurityConfig(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/super-admin", "/admin")
                    .hasRole("SUPER_ADMIN")
                .antMatchers("/admin")
                    .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/admin")
                .usernameParameter("adminId")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) //세션 날리기
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }
}
