package com.example.bak.domain.board.service

import com.example.bak.domain.board.controller.dto.request.BoardAddRequest
import com.example.bak.domain.board.controller.dto.response.BoardListResponse
import com.example.bak.domain.board.controller.dto.response.BoardMaximumResponse
import com.example.bak.domain.board.controller.dto.response.BoardMinimumResponse
import com.example.bak.domain.board.entity.Board
import com.example.bak.domain.board.repository.BoardRepository
import com.example.bak.domain.board.service.exception.BoardNotFoundException
import com.example.bak.domain.user.service.UserProvider
import com.example.bak.domain.user.service.exception.NoPermissionException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BoardService(

    private val boardRepository: BoardRepository,

    private val userProvider: UserProvider
) {

    fun addBoard(request: BoardAddRequest) {
        boardRepository.save(
            request.run {
                Board(title, content, userProvider.currentUser())
            })
    }

    fun deleteBoard(boardId: Long) {

        val board: Board = boardRepository.findById(boardId).getOrNull() ?: throw BoardNotFoundException

        if (userProvider.currentUser().accountId != board.user.accountId)
            throw NoPermissionException

        boardRepository.deleteById(boardId)
    }

    fun findBoardList(): BoardListResponse {

        return BoardListResponse(
            boardRepository.findAll().map {
                BoardMinimumResponse.of(it)
            }.toList()
        )
    }

    fun findBoard(boardId: Long): BoardMaximumResponse {
        val board: Board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException
        return BoardMaximumResponse.of(board)
    }
}