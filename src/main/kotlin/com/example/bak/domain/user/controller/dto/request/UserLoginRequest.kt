package com.example.bak.domain.user.controller.dto.request

data class UserLoginRequest(
    val accountId: String,
    val password: String
)
