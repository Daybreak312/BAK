package com.example.bak.domain.board.controller.dto.response

import com.example.bak.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardMinimumResponse(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val userName: String
) {
    companion object {

        fun of(board: Board): BoardMinimumResponse =
            with(board) {
                BoardMinimumResponse(
                    title = title,
                    content = content,
                    createdAt = createdAt,
                    userName = user.name
                )
            }
    }
}