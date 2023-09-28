package com.example.bak.domain.chat.entity

import com.example.bak.domain.user.entity.User
import com.example.bak.global.base.BaseTimeEntity
import javax.persistence.*

@Entity(name = "tbl_chat")
data class Chat(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val sender: User,

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    val chatRoom: ChatRoom,

    @Column(name = "message", nullable = false)
    val message: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
) : BaseTimeEntity()
