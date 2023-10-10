package com.example.bak.global.config.security.jwt

import com.example.bak.domain.auth.entity.RefreshToken
import com.example.bak.domain.auth.persistence.RefreshTokenRepository
import com.example.bak.domain.user.presentation.dto.TokenResponse
import com.example.bak.global.config.security.jwt.env.JwtProperty
import com.example.bak.global.exception.ExpiredTokenException
import com.example.bak.global.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperty: JwtProperty,

    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun createToken(accountId: String): TokenResponse {

        val accessToken = generateAccessToken(accountId)
        val refreshToken = generateRefreshToken()

        refreshTokenRepository.deleteById(accountId)
        refreshTokenRepository.save(RefreshToken(accountId, refreshToken))

        return TokenResponse(accessToken, refreshToken)
    }

    fun reissue(token: String): TokenResponse {

        val refreshToken: RefreshToken = refreshTokenRepository.findByToken(token) ?: throw InvalidTokenException

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

    private fun generateRefreshToken(): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtProperty.refreshTokenExp))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String {

        val token: String = request.getHeader(jwtProperty.header)?: throw InvalidTokenException

        if (token.startsWith(jwtProperty.prefix) && token.length > jwtProperty.prefix.length)
            return token.substring(7)
        throw InvalidTokenException
    }

    fun getAccountId(token: String): String {

        val tokenBodyClaims: Claims

        try {
            tokenBodyClaims = Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException
        }

        if (tokenBodyClaims.expiration.before(Date()))
            throw ExpiredTokenException

        return tokenBodyClaims.subject ?: throw InvalidTokenException
    }
}