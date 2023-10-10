package com.example.bak.domain.board.presentation.dto.response

import com.example.bak.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardMaximumResponse(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val userName: String
) {
    companion object {

        fun of(board: Board): BoardMaximumResponse =
            with(board) {
                BoardMaximumResponse(
                    title = title,
                    content = content,
                    createdAt = createdAt,
                    userName = user.name
                )
            }
    }
}