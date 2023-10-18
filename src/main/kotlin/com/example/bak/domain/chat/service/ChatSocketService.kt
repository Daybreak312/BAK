package com.example.bak.domain.chat.service

import com.example.bak.domain.chat.entity.Chat
import com.example.bak.domain.chat.persistence.ChatRepository
import com.example.bak.domain.chat.persistence.ChatRoomJoinerRepository
import com.example.bak.domain.chat.persistence.ChatRoomRepository
import com.example.bak.domain.chat.presentation.dto.request.SendMessageDto
import com.example.bak.domain.chat.presentation.dto.response.ReceiveMessageDto
import com.example.bak.domain.chat.service.exception.ChatMessageNullException
import com.example.bak.domain.chat.service.exception.ChatRoomNoPermissionException
import com.example.bak.domain.chat.service.exception.ChatRoomNotFoundException
import com.example.bak.domain.chat.service.exception.ChatRoomNullException
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

    private val logger = LogFactory.getLog(javaClass)

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

        val dto = objectMapper.readValue(message, SendMessageDto::class.java)

        dto.chatRoom ?: throw ChatRoomNullException
        dto.message ?: throw ChatMessageNullException

        val sender = userRepository.findByAccountId(session.userPrincipal.name) ?: throw UserNotFoundException

        val sendTargetChatRoom = chatRoomRepository.findByIdOrNull(dto.chatRoom) ?: throw ChatRoomNotFoundException

        if (!chatRoomJoinerRepository.existsByChatRoomAndUser(sendTargetChatRoom, sender)) throw ChatRoomNoPermissionException

        val receiveClients: List<Session> =
            chatRoomJoinerRepository.findAllByChatRoom(sendTargetChatRoom)
                .map { joiner -> clients.first { it.userPrincipal.name == joiner.user.accountId } }
                .filter { it.userPrincipal.name != sender.accountId }

        logger.info("${sendTargetChatRoom.name} - id \"${sendTargetChatRoom.id}\" : will receive.")

        val chat = chatRepository.save(
            Chat(
                user = sender,
                chatRoom = sendTargetChatRoom,
                message = dto.message
            )
        )

        val writtenChat = objectMapper.writeValueAsString(ReceiveMessageDto.of(chat))

        receiveClients.map { it.basicRemote.sendText(writtenChat) }
    }
}