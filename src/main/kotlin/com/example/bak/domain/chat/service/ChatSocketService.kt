package com.example.bak.domain.chat.service

import com.example.bak.domain.chat.entity.Chat
import com.example.bak.domain.chat.entity.ChatRoom
import com.example.bak.domain.chat.persistence.ChatRepository
import com.example.bak.domain.chat.persistence.ChatRoomJoinerRepository
import com.example.bak.domain.chat.persistence.ChatRoomRepository
import com.example.bak.domain.chat.presentation.dto.request.SendMessageDto
import com.example.bak.domain.chat.presentation.dto.response.ReceiveMessageDto
import com.example.bak.domain.chat.service.exception.ChatMessageNullException
import com.example.bak.domain.chat.service.exception.ChatRoomNotFoundException
import com.example.bak.domain.chat.service.exception.ChatSendRoomNullException
import com.example.bak.domain.user.entity.User
import com.example.bak.domain.user.repository.UserRepository
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
        logger.info("new session wired : ${session.userPrincipal.name}, wire count > ${clients.size}")
    }

    @OnClose
    fun socketClose(session: Session) {
        clients.remove(session)
        logger.info("session logout : ${session.userPrincipal.name}")
    }

    @OnMessage
    fun socketSend(session: Session, message: String) {

        val dto: SendMessageDto = objectMapper.readValue(message, SendMessageDto::class.java)
        logger.info("new message : ${session.userPrincipal.name} - ${dto.message} to ${dto.chatRoom}")

        dto.chatRoom ?: throw ChatSendRoomNullException
        dto.message ?: throw ChatMessageNullException

        val sender: User = userRepository.findByAccountId(session.userPrincipal.name) ?: throw UserNotFoundException
        // userProvider (userFacade)의 currentUser 메소드 사용시 SecurityContext의 Authentication이 null로 떠서 임시로 대체.

        val chatRoom: ChatRoom = chatRoomRepository.findByIdOrNull(dto.chatRoom) ?: throw ChatRoomNotFoundException

        val receiveClients: List<Session> =
            chatRoomJoinerRepository.findAllByChatRoom(chatRoom) // join용 entity 리스트
                .map { joiner -> clients.first { it.userPrincipal.name == joiner.user.accountId } }
        // joiner 리스트로부터 추출한 user의 accountId와 일치하는 session들을 리스트로 추출

        val chat = Chat(
            sender = sender,
            chatRoom = chatRoom,
            message = dto.message
        )

        logger.info("$chatRoom : will receive.")

        receiveClients.map {
            it.basicRemote.sendText(
                objectMapper.writeValueAsString(
                    ReceiveMessageDto.of(chat)
                )
            )
        }
        chatRepository.save(chat)
    }
}