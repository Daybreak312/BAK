package com.example.bak.domain.chat.presentation.dto.request

data class CreateChatRoomRequest(
    val accountIds: List<String>,
    val chatRoomName: String
)
