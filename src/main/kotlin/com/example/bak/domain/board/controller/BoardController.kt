package com.example.bak.domain.board.controller

import com.example.bak.domain.board.controller.dto.request.BoardAddRequest
import com.example.bak.domain.board.controller.dto.response.BoardListResponse
import com.example.bak.domain.board.controller.dto.response.BoardMaximumResponse
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
    fun boardAdd(@RequestBody request: BoardAddRequest) {
        boardService.addBoard(request)
    }

    @DeleteMapping("/{boardId}")
    fun boardDelete(@PathVariable boardId: Long) {
        boardService.deleteBoard(boardId)
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