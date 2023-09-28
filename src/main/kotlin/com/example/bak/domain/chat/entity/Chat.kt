package com.example.bak.domain.chat.entity

import com.example.bak.domain.chat.entity.idclass.ChatId
import com.example.bak.domain.user.entity.User
import com.example.bak.global.base.BaseTimeEntity
import javax.persistence.*

@IdClass(ChatId::class)
@Entity(name = "tbl_chat")
data class Chat(

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    val chatRoom: ChatRoom,

    @Column(name = "message", nullable = false)
    val message: String
) : BaseTimeEntity()
