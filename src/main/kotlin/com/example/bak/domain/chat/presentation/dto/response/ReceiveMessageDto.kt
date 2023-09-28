package com.example.bak.domain.chat.presentation.dto.response

import com.example.bak.domain.chat.entity.Chat
import java.time.LocalDateTime

data class ReceiveMessageDto(

    val message: String,

    val receiveFrom: String,

    val sendTime: LocalDateTime
) {
    companion object {
        fun of(chat: Chat): ReceiveMessageDto =
            ReceiveMessageDto(
                message = chat.message,
                receiveFrom = chat.sender.name,
                sendTime = chat.createdAt
            )
    }
}