package com.example.bak.domain.board.service

import com.example.bak.domain.board.controller.dto.request.BoardAddRequest
import com.example.bak.domain.board.entity.Board
import com.example.bak.domain.board.repository.BoardRepository
import com.example.bak.domain.user.service.UserProvider
import org.springframework.stereotype.Service

@Service
class BoardService(

    private val boardRepository: BoardRepository,

    private val userProvider: UserProvider
) {

    fun addBoard(request: BoardAddRequest) {
        boardRepository.save(
            Board(
                title = request.title,
                content = request.content,
                user = userProvider.currentUser()!!
            )
        )
    }
}