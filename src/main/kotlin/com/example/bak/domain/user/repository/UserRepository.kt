package com.example.bak.domain.user.repository

import com.example.bak.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long?> {

    fun findByAccountId(accountId: String): User?

    fun existsByAccountId(accountId: String): Boolean
}