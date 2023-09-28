package com.example.bak.domain.chat.entity

import com.example.bak.domain.user.entity.User
import javax.persistence.*

@Entity(name = "tbl_room_joiner")
data class ChatRoomJoiner(

    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    val chatRoom: ChatRoom,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
