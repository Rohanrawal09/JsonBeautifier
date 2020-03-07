package com.rouge.dev.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Cretated By Rohan Rawal on 23-01-2020
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//@formatter:off
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER");

        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/download").authenticated().and()
                .formLogin().defaultSuccessUrl("/",true).failureUrl("/login");

        http.formLogin().loginPage("/login.html");
    }
}
