package com.example.bak.domain.board.service

import com.example.bak.domain.board.controller.dto.request.BoardCreateRequest
import com.example.bak.domain.board.controller.dto.request.BoardUpdateRequest
import com.example.bak.domain.board.controller.dto.response.BoardListResponse
import com.example.bak.domain.board.controller.dto.response.BoardMaximumResponse
import com.example.bak.domain.board.controller.dto.response.BoardMinimumResponse
import com.example.bak.domain.board.entity.Board
import com.example.bak.domain.board.repository.BoardRepository
import com.example.bak.domain.board.service.exception.BoardNoPermissionException
import com.example.bak.domain.board.service.exception.BoardNotFoundException
import com.example.bak.domain.user.service.UserProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(

    private val boardRepository: BoardRepository,

    private val userProvider: UserProvider
) {

    @Transactional
    fun createBoard(request: BoardCreateRequest) {
        boardRepository.save(
            request.run {
                Board(title, content, userProvider.currentUser())
            })
    }

    @Transactional
    fun deleteBoard(boardId: Long) {

        val board: Board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException

        if (userProvider.currentUser().accountId != board.user.accountId)
            throw BoardNoPermissionException

        boardRepository.deleteById(boardId)
    }

    @Transactional
    fun updateBoard(boardId: Long, request: BoardUpdateRequest) {

        val board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException

        if (board.user != userProvider.currentUser())
            throw BoardNoPermissionException

        request.title?.let { board.updateTitle(it) }
        request.content?.let { board.updateContent(it) }
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