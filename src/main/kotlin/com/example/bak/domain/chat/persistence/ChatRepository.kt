package com.example.bak.domain.chat.persistence

import com.example.bak.domain.chat.entity.Chat
import com.example.bak.domain.chat.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<Chat, Long?> {

    fun findAllByChatRoom(chatRoom: ChatRoom): List<Chat>
}