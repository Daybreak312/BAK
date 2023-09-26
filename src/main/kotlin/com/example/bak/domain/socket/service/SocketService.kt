package com.example.bak.domain.socket.service

import com.example.bak.domain.socket.presentation.dto.request.SendMessageDto
import com.example.bak.domain.socket.presentation.dto.response.ReceiveMessageDto
import com.example.bak.domain.socket.service.exception.ChatMessageNullException
import com.example.bak.domain.socket.service.exception.ChatSendToNullException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/chat")
@Service
class SocketService {
    companion object {

        private val objectMapper: ObjectMapper = ObjectMapper()

        private val clients: ArrayList<Session> = ArrayList()
    }

    private val logger = LogFactory.getLog(javaClass)

    @OnOpen
    fun socketOpen(session: Session) {
        if (clients.contains(session))
            logger.info("already wired session : ${session.userPrincipal.name}")
        else {
            clients.add(session)
            logger.info("new session wired : ${session.userPrincipal.name}, wire count > ${clients.size}")
        }
    }

    @OnClose
    fun socketClose(session: Session) {
        clients.remove(session)
        logger.info("session logout : ${session.userPrincipal.name}")
    }

    @OnMessage
    fun socketSend(session: Session, message: String) {

        val dto: SendMessageDto = objectMapper.readValue(message, SendMessageDto::class.java)
        logger.info("new message : ${session.userPrincipal.name} - ${dto.message} to ${dto.sendTo}")

        dto.message ?: throw ChatMessageNullException
        if (dto.sendTo.isNullOrBlank())
            throw ChatSendToNullException

        for (client in clients) {

            logger.info("${client.userPrincipal.name} : send?")

            if (client.userPrincipal.name == dto.sendTo)
                client.basicRemote.sendText(
                    objectMapper.writeValueAsString(
                        ReceiveMessageDto(
                            message = dto.message,
                            receiveFrom = session.userPrincipal.name
                        )
                    )
                )
        }
    }
}
