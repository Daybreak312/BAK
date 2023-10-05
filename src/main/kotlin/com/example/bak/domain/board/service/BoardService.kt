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
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(

    private val boardRepository: BoardRepository,

    private val userProvider: UserProvider
) {

    @Transactional
    fun addBoard(request: BoardAddRequest) {
        boardRepository.save(
            request.run {
                Board(title, content, userProvider.currentUser())
            })
    }

    @Transactional
    fun deleteBoard(boardId: Long) {

        val board: Board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException

        if (userProvider.currentUser().accountId != board.user.accountId)
            throw NoPermissionException

        boardRepository.deleteById(boardId)
    }

    @Transactional(readOnly = true)
    fun findBoard(boardId: Long): BoardMaximumResponse =
        BoardMaximumResponse.of(
            boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException
        )

    @Transactional(readOnly = true)
    fun findBoardList(): BoardListResponse =
        BoardListResponse(
            boardRepository.findAll().map(BoardMinimumResponse.Companion::of).toList()
        )
}