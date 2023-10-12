package com.example.bak.domain.board.service

import com.example.bak.domain.board.entity.Board
import com.example.bak.domain.board.persistence.BoardRepository
import com.example.bak.domain.board.presentation.dto.request.BoardCreateRequest
import com.example.bak.domain.board.presentation.dto.request.BoardUpdateRequest
import com.example.bak.domain.board.presentation.dto.response.BoardListResponse
import com.example.bak.domain.board.presentation.dto.response.BoardMaximumResponse
import com.example.bak.domain.board.presentation.dto.response.BoardMinimumResponse
import com.example.bak.domain.board.service.exception.BoardNoPermissionException
import com.example.bak.domain.board.service.exception.BoardNotFoundException
import com.example.bak.domain.user.service.UserProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BoardService(

    private val boardRepository: BoardRepository,

    private val userProvider: UserProvider
) {

    fun createBoard(request: BoardCreateRequest) {
        boardRepository.save(
            request.run {
                Board(title, content, userProvider.currentUser())
            })
    }

    fun deleteBoard(boardId: Long) {

        val board: Board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException

        if (userProvider.currentUser().accountId != board.user.accountId)
            throw BoardNoPermissionException

        boardRepository.deleteById(boardId)
    }

    fun updateBoard(boardId: Long, request: BoardUpdateRequest) {

        val board = boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException

        if (board.user != userProvider.currentUser())
            throw BoardNoPermissionException

        request.title?.let { board.title = it }
        request.content?.let { board.content = it }
    }

    @Transactional(readOnly = true)
    fun findBoard(boardId: Long): BoardMaximumResponse =
        BoardMaximumResponse.of(
            boardRepository.findByIdOrNull(boardId) ?: throw BoardNotFoundException
        )

    @Transactional(readOnly = true)
    fun findBoardList(): BoardListResponse =
        BoardListResponse(
            boardRepository.findAll().map(BoardMinimumResponse::of).toList()
        )
}