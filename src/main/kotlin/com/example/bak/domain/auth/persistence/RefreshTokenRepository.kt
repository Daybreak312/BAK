package com.example.bak.domain.auth.persistence

import com.example.bak.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}