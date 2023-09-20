package com.example.bak.domain.user.controller.dto.request

data class UserSignRequest(
    val name: String,
    val accountId: String,
    val password: String,
    val passwordCheck: String
)
