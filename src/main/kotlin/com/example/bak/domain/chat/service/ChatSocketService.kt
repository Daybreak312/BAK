package com.example.bak.domain.chat.service

import com.example.bak.domain.chat.entity.Chat
import com.example.bak.domain.chat.entity.ChatRoom
import com.example.bak.domain.chat.persistence.ChatRepository
import com.example.bak.domain.chat.persistence.ChatRoomJoinerRepository
import com.example.bak.domain.chat.persistence.ChatRoomRepository
import com.example.bak.domain.chat.presentation.dto.request.SendChatDto
import com.example.bak.domain.chat.presentation.dto.response.ReceiveChatDto
import com.example.bak.domain.chat.service.exception.ChatMessageNullException
import com.example.bak.domain.chat.service.exception.ChatRoomNoPermissionException
import com.example.bak.domain.chat.service.exception.ChatRoomNotFoundException
import com.example.bak.domain.chat.service.exception.ChatRoomNullException
import com.example.bak.domain.user.entity.User
import com.example.bak.domain.user.persistence.UserRepository
import com.example.bak.domain.user.service.exception.UserNotFoundException
import com.example.bak.global.config.socket.ServerEndpointConfigurator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.commons.logging.LogFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value = "/chat", configurator = ServerEndpointConfigurator::class)
@Service
class ChatSocketService(
    private val chatRepository: ChatRepository,

    private val chatRoomRepository: ChatRoomRepository,

    private val chatRoomJoinerRepository: ChatRoomJoinerRepository,

    private val userRepository: UserRepository,

    private val objectMapper: ObjectMapper
) {

    companion object {

        private val clients: ArrayList<Session> = ArrayList()
    }

    @OnOpen
    fun socketOpen(session: Session) {
        objectMapper.registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        clients.add(session)
    }

    @OnClose
    fun socketClose(session: Session) {
        clients.remove(session)
    }

    @OnMessage
    fun socketSend(session: Session, message: String) {

        val dto = objectMapper.readValue(message, SendChatDto::class.java)

        val sender = userRepository.findByAccountId(session.userPrincipal.name) ?: throw UserNotFoundException
        val sendTargetChatRoom = chatRoomRepository.findByIdOrNull(dto.chatRoomId) ?: throw ChatRoomNotFoundException
        if (!chatRoomJoinerRepository.existsByChatRoomAndUser(sendTargetChatRoom, sender))
            throw ChatRoomNoPermissionException

        val chatRoomParticipants = orderChatRoomParticipants(sendTargetChatRoom, sender)
        val chat = chatRepository.save(Chat(sender = sender, chatRoom = sendTargetChatRoom, message = dto.message))

        sendChat(chat, chatRoomParticipants)
    }

    private fun orderChatRoomParticipants(sendTargetChatRoom: ChatRoom, sender: User) =
        chatRoomJoinerRepository.findAllByChatRoom(sendTargetChatRoom)
            .map { joiner -> clients.first { it.userPrincipal.name == joiner.user.accountId } }
            .filter { it.userPrincipal.name != sender.accountId }

    private fun sendChat(chat: Chat, chatRoomParticipants: List<Session>) {
        val chatDto = objectMapper.writeValueAsString(ReceiveChatDto.of(chat))
        chatRoomParticipants.map { it.basicRemote.sendText(chatDto) }
    }
}