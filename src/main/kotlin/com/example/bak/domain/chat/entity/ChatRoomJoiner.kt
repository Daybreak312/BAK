package com.example.bak.domain.chat.entity

import com.example.bak.domain.chat.entity.idclass.ChatRoomJoinerId
import com.example.bak.domain.user.entity.User
import javax.persistence.*

@IdClass(ChatRoomJoinerId::class)
@Entity(name = "tbl_room_joiner")
data class ChatRoomJoiner   (

    @Id
    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    val chatRoom: ChatRoom,

    @Id
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)
