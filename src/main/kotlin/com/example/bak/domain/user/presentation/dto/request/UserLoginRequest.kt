package com.example.bak.domain.user.presentation.dto.request

data class UserLoginRequest(
    val accountId: String,
    val password: String
)
