package com.example.bak.global.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Suppress("DEPRECATION")
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf().disable()
            .formLogin().disable()
            .cors().and()

            .authorizeRequests()
            .antMatchers("/auth/*").permitAll()
            .antMatchers("/mail/*").permitAll()
            .anyRequest().authenticated()
            .and()

            .exceptionHandling().and()

            .build()
    }
}