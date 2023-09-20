package com.example.bak.domain.board.controller

import com.example.bak.domain.board.controller.dto.request.BoardAddRequest
import com.example.bak.domain.board.service.BoardService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/board")
@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    fun boardAdd(request: BoardAddRequest) = boardService.addBoard(request)
}