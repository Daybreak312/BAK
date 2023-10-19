package com.example.bak.domain.chat.presentation.dto.response

import com.example.bak.domain.chat.entity.Chat
import java.time.LocalDateTime

data class ReceiveChatDto(

    val message: String,

    val sender: String,

    val chatRoomName: String,

    val chatRoomId: Long,

    val sendTime: LocalDateTime
) {
    companion object {
        fun of(chat: Chat): ReceiveChatDto =
            ReceiveChatDto(
                message = chat.message,
                sender = chat.user.name,
                chatRoomName = chat.chatRoom.name,
                chatRoomId = chat.chatRoom.id!!,
                sendTime = chat.createdAt
            )
    }
}