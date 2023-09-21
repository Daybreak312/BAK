package com.example.bak.domain.user.entity

import com.example.bak.domain.board.entity.Board
import com.example.bak.domain.user.entity.role.Role
import javax.persistence.*

@Entity(name = "tbl_user")
class User(

    @Column(name = "account_id", nullable = false)
    var accountId: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role", nullable = false)
    val role: Role,

    @OneToMany(mappedBy = "user")
    @Column(name = "boards", nullable = false)
    var boards: List<Board> = ArrayList(),

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)