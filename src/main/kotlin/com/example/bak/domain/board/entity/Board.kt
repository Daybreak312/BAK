package com.example.bak.domain.board.entity

import com.example.bak.domain.user.entity.User
import com.example.bak.global.base.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE tbl_board SET deleted = true WHERE id = ?")
@Entity(name = "tbl_board")
data class Board(

    @Column(name = "title", length = 50, nullable = false)
    var title: String,

    @Column(name = "content", length = 1000, nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()