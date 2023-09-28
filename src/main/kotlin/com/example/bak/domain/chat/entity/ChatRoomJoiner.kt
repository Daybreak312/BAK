package com.example.bak.domain.chat.entity

import com.example.bak.domain.user.entity.User
import java.io.Serializable
import javax.persistence.*

@IdClass(ChatRoomJoiner.IdClass::class)
@Entity(name = "tbl_room_joiner")
open class ChatRoomJoiner   (
    room: ChatRoom,
    user: User
) {
    @Id @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    var chatRoom: ChatRoom = room
        protected set

    @Id @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    data class IdClass (
        var chatRoom: Long? = null,
        var user: Long? = null
    ): Serializable

    fun id() = IdClass(
        this.chatRoom.id,
        this.user.id
    )
}
