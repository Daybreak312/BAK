package com.example.bak.global.config.security.auth

import com.example.bak.domain.user.repository.UserRepository
import com.example.bak.global.config.security.auth.exception.UserNameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails
    = CustomUserDetails(userRepository.findByAccountId(username) ?: throw UserNameNotFoundException)
}