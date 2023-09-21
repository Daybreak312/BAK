package com.example.bak.domain.board.entity

import com.example.bak.domain.user.entity.User
import com.example.bak.global.base.BaseTimeEntity
import javax.persistence.*

@Entity(name = "tbl_board")
class Board(

    @Column(name = "title", length = 50, nullable = false)
    var title: String,

    @Column(name = "content", length = 1000, nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Id
    @Column(name = "board_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()