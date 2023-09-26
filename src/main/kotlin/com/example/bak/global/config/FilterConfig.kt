package com.example.bak.global.config

import com.example.bak.global.config.security.auth.CustomUserDetailService
import com.example.bak.global.config.security.jwt.JwtFilter
import com.example.bak.global.config.security.jwt.JwtTokenProvider
import com.example.bak.global.error.filter.ExceptionFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(

    private val jwtTokenProvider: JwtTokenProvider,

    private val objectMapper: ObjectMapper,

    private val userDetailService: CustomUserDetailService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {

        val jwtTokenFilter = JwtFilter(jwtTokenProvider, userDetailService)

        val exceptionFilter = ExceptionFilter(objectMapper)

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionFilter, JwtFilter::class.java)
    }
}