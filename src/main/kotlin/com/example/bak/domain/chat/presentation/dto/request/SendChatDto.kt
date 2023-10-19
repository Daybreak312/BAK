package com.example.bak.domain.chat.presentation.dto.request

data class SendChatDto(
    val message: String,
    val chatRoomId: Long
)