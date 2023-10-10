package com.example.bak.domain.user.presentation.dto.request

data class UserSignRequest(
    val name: String,
    val accountId: String,
    val password: String,
    val passwordCheck: String
)
