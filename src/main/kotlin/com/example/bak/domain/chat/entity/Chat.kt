package com.example.bak.domain.chat.entity

import com.example.bak.domain.user.entity.User
import com.example.bak.global.base.BaseTimeEntity
import java.io.Serializable
import javax.persistence.*

@IdClass(Chat.IdClass::class)
@Entity(name = "tbl_chat")
open class Chat(

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
) : BaseTimeEntity() {

    data class IdClass(
        var chatRoom: Long? = null,
        var user: Long? = null
    ) : Serializable

    fun id() = IdClass(
        this.chatRoom.id,
        this.user.id
    )
}
