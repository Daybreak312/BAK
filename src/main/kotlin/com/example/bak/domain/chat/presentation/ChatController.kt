package com.example.bak.domain.chat.presentation

import com.example.bak.domain.chat.presentation.dto.request.CreateChatRoomRequest
import com.example.bak.domain.chat.service.ChatService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/chat")
@RestController
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping("/create")
    fun chatRoomCreate(@RequestBody request: CreateChatRoomRequest) {
        chatService.createChatRoom(request)
    }
}