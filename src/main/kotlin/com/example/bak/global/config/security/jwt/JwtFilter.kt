package com.example.bak.global.config.security.jwt

import com.example.bak.global.config.security.auth.CustomUserDetailService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(

    private val jwtTokenProvider: JwtTokenProvider,

    private val userDetailService: CustomUserDetailService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        logger.info("* LOG | Jwt Filter run | ${request.method} ${request.requestURL}")
        if (!request.requestURI.startsWith("/auth")) {

            val token: String = jwtTokenProvider.resolveToken(request)

            val accountId: String = jwtTokenProvider.getAccountId(token)

            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                accountId, "", userDetailService.loadUserByUsername(accountId).authorities
            )

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}