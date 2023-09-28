package com.example.bak.domain.chat.service

import com.example.bak.domain.chat.entity.ChatRoom
import com.example.bak.domain.chat.entity.ChatRoomJoiner
import com.example.bak.domain.chat.persistence.ChatRoomJoinerRepository
import com.example.bak.domain.chat.persistence.ChatRoomRepository
import com.example.bak.domain.chat.presentation.dto.request.CreateChatRoomRequest
import com.example.bak.domain.user.repository.UserRepository
import com.example.bak.domain.user.service.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ChatService(

    private val userRepository: UserRepository,

    private val chatRoomRepository: ChatRoomRepository,

    private val chatRoomJoinerRepository: ChatRoomJoinerRepository
) {

    @Transactional
    fun createChatRoom(request: CreateChatRoomRequest) {

        val chatRoom = chatRoomRepository.save(ChatRoom(name = request.chatRoomName))

        request.accountIdList.map {
            chatRoomJoinerRepository.save(
                ChatRoomJoiner(chatRoom, userRepository.findByAccountId(it) ?: throw UserNotFoundException)
            )
        }
    }
}