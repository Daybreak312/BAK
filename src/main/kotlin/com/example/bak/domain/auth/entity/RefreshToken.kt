package com.example.bak.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 432000)
data class RefreshToken(

    @Id
    val accountId: String,

    val token: String
)
