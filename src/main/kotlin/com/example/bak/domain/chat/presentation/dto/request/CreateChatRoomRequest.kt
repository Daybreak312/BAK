package com.example.bak.domain.chat.presentation.dto.request

data class CreateChatRoomRequest(

    val accountIdList: List<String>,

    val chatRoomName: String
)
