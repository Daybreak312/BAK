package com.example.bak.domain.chat.entity

import javax.persistence.*

@Entity(name = "tbl_room")
data class ChatRoom(

    @OneToMany(mappedBy = "chatRoom")
    var joiner: MutableList<ChatRoomJoiner> = mutableListOf(),

    @Column(name = "room_name", nullable = false)
    val name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)