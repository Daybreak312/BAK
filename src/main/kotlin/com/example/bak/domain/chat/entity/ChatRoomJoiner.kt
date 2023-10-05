package com.example.bak.domain.chat.entity

import com.example.bak.domain.user.entity.User
import java.io.Serializable
import javax.persistence.*

@IdClass(ChatRoomJoiner.IdClass::class)
@Entity(name = "tbl_room_joiner")
class ChatRoomJoiner(

    @Id @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    var chatRoom: ChatRoom,

    @Id @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) {

    data class IdClass(
        var chatRoom: Long? = null,
        var user: Long? = null
    ) : Serializable

    private fun id() = IdClass(
        this.chatRoom.id,
        this.user.id
    )
}