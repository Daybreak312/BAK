package com.example.bak.domain.chat.presentation.dto.response

import com.example.bak.domain.chat.entity.Chat
import java.time.LocalDateTime

data class ReceiveMessageDto(

    val message: String,

    val sendBy: String,

    val chatRoomName: String,

    val chatRoomId: Long,

    val sendTime: LocalDateTime
) {
    companion object {
        fun of(chat: Chat): ReceiveMessageDto =
            ReceiveMessageDto(
                message = chat.message,
                sendBy = chat.user.name,
                chatRoomName = chat.chatRoom.name,
                chatRoomId = chat.chatRoom.id!!,
                sendTime = chat.createdAt
            )
    }
}