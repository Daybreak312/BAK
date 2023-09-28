package com.example.bak.domain.chat.entity.idclass

import java.io.Serializable
import java.util.*
import javax.persistence.Column

class ChatId : Serializable {

    @Column(name = "user_id", nullable = false)
    val user: Long? = null

    @Column(name = "room_id", nullable = false)
    val chatRoom: Long? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val chatId: ChatId = o as ChatId
        return Objects.equals(user, chatId.user) && Objects.equals(
            chatRoom, chatId.chatRoom
        )
    }

    override fun hashCode(): Int {
        return Objects.hash(user, chatRoom)
    }
}