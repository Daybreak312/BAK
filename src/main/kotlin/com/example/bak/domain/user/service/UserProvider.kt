package com.example.bak.domain.user.service

import com.example.bak.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserProvider(
    private val userRepository: UserRepository
) {
    fun currentUser() = userRepository.findByAccountId(SecurityContextHolder.getContext().authentication!!.name)
}