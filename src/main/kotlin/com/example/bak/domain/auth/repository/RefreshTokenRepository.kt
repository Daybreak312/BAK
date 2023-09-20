package com.example.bak.domain.auth.repository

import com.example.bak.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}