package com.example.bak.global.config.redis

import com.example.bak.global.config.redis.env.RedisProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig(
    private val redisProperty: RedisProperty
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperty.host, redisProperty.port)
    }

    // @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate: RedisTemplate<String, String> = RedisTemplate()
        return redisTemplate.run {
            setConnectionFactory(redisConnectionFactory())
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
            return@run this
        }
    }
}