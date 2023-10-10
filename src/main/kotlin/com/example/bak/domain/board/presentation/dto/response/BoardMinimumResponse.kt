package com.example.bak.domain.board.presentation.dto.response

import com.example.bak.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardMinimumResponse(
    val boardId: Long,
    val title: String,
    val createdAt: LocalDateTime,
    val userName: String
) {
    companion object {

        fun of(board: Board): BoardMinimumResponse =
            board.run {
                BoardMinimumResponse(
                    boardId = id!!,
                    title = title,
                    createdAt = createdAt,
                    userName = user.name
                )
            }
    }
}