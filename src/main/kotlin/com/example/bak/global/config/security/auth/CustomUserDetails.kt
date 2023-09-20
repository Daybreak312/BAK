package com.example.bak.global.config.security.auth

import com.example.bak.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    = mutableListOf(GrantedAuthority { user.role.name })

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.accountId

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}