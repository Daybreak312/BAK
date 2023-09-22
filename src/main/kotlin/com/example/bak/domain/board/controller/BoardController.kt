package com.example.bak.domain.board.controller

import com.example.bak.domain.board.controller.dto.request.BoardAddRequest
import com.example.bak.domain.board.service.BoardService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/board")
@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    fun boardAdd(@RequestBody request: BoardAddRequest) {
        boardService.addBoard(request)
    }

    @DeleteMapping("/{boardId}")
    fun boardDelete(@PathVariable boardId: Long) {
        boardService.deleteBoard(boardId)
    }
}