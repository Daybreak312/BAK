package com.example.bak.global.config.security.jwt.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
data class JwtProperty (
    val secretKey: String,
    val accessTokenExp: Long,
    val refreshTokenExp: Long,

    val header: String,
    val prefix: String
)