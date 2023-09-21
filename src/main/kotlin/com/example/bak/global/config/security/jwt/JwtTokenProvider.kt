package com.example.bak.global.config.security.jwt

import com.example.bak.domain.auth.entity.RefreshToken
import com.example.bak.domain.auth.repository.RefreshTokenRepository
import com.example.bak.domain.user.controller.dto.TokenResponse
import com.example.bak.global.config.security.jwt.env.JwtProperty
import com.example.bak.global.exception.ExpiredTokenException
import com.example.bak.global.exception.InvalidTokenException
import com.example.bak.global.exception.NotBearerTokenException
import com.example.bak.global.exception.TokenIsNullException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

// JWT 토큰 제공자, RTR 방식을 사용
@Component
class JwtTokenProvider(
    private val jwtProperty: JwtProperty,

    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun createToken(accountId: String): TokenResponse {

        val accessToken = generateAccessToken(accountId)
        val refreshToken = generateRefreshToken(accountId)

        refreshTokenRepository.save(
            RefreshToken(
                accountId = accountId, token = refreshToken
            )
        )

        return TokenResponse(accessToken, refreshToken)
    }

    fun reissue(token: String): TokenResponse {

        val refreshToken: RefreshToken = refreshTokenRepository.findByToken(token) ?: throw InvalidTokenException
        refreshTokenRepository.deleteById(refreshToken.accountId)

        return createToken(refreshToken.accountId)
    }

    private fun generateAccessToken(accountId: String): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
            .setSubject(accountId)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtProperty.accessTokenExp))
            .compact()
    }

    private fun generateRefreshToken(accountId: String): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
            .setSubject(accountId)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtProperty.refreshTokenExp))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String {

        val token: String? = request.getHeader(jwtProperty.header)
        if (token.isNullOrBlank())
            throw TokenIsNullException

        if (token.startsWith(jwtProperty.prefix) && token.length > jwtProperty.prefix.length)
            return token.substring(7)
        throw NotBearerTokenException
    }

    fun getAccountId(token: String): String {

        val tokenBodyClaims: Claims

        try { // 토큰이 아닐 경우
            tokenBodyClaims = Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException
        }

        if (tokenBodyClaims.expiration.before(Date()))
            throw ExpiredTokenException // 토큰이 만료되었을 경우

        return tokenBodyClaims.subject
    }
}