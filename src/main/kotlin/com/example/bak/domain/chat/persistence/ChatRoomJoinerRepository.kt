package com.example.bak.domain.chat.persistence

import com.example.bak.domain.chat.entity.ChatRoom
import com.example.bak.domain.chat.entity.ChatRoomJoiner
import com.example.bak.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomJoinerRepository : JpaRepository<ChatRoomJoiner, ChatRoomJoiner.IdClass> {

    fun findAllByChatRoom(chatRoom: ChatRoom): List<ChatRoomJoiner>

    fun findAllByUser(user: User): List<ChatRoomJoiner>

    fun existsByChatRoomAndUser(chatRoom: ChatRoom, user: User): Boolean
}