package com.example.bak.global.config.redis.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "spring.redis")
@ConstructorBinding
data class RedisProperty(
    val host: String,
    val port: Int
)