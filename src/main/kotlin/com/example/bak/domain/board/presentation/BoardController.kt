package com.example.bak.domain.board.presentation

import com.example.bak.domain.board.presentation.dto.request.BoardCreateRequest
import com.example.bak.domain.board.presentation.dto.request.BoardUpdateRequest
import com.example.bak.domain.board.presentation.dto.response.BoardListResponse
import com.example.bak.domain.board.presentation.dto.response.BoardMaximumResponse
import com.example.bak.domain.board.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/board")
@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun boardCreate(@RequestBody request: BoardCreateRequest) {
        boardService.createBoard(request)
    }

    @DeleteMapping("/{boardId}")
    fun boardDelete(@PathVariable boardId: Long) {
        boardService.deleteBoard(boardId)
    }

    @PutMapping("/{boardId}")
    fun boardUpdate(@PathVariable boardId: Long, @RequestBody request: BoardUpdateRequest) {
        boardService.updateBoard(boardId, request)
    }

    @GetMapping
    fun findBoardList(): BoardListResponse {
        return boardService.findBoardList()
    }

    @GetMapping("/{boardId}")
    fun findBoard(@PathVariable boardId: Long): BoardMaximumResponse {
        return boardService.findBoard(boardId)
    }
}