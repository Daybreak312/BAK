package com.example.bak.global.config.security

import com.example.bak.global.config.security.auth.CustomUserDetailService
import com.example.bak.global.config.security.jwt.JwtTokenProvider
import com.example.mukgen.global.config.FilterConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Suppress("DEPRECATION")
@Configuration
@EnableWebSecurity
class SecurityConfig(

    private val jwtTokenProvider: JwtTokenProvider,

    private val objectMapper: ObjectMapper,

    private val userDetailService: CustomUserDetailService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf().disable()
            .formLogin().disable()
            .cors().and()
            .exceptionHandling().and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()

            .apply(FilterConfig(jwtTokenProvider, objectMapper, userDetailService))
            .and()

            .build()
    }
}