package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object ChatSendRoomNullException : GlobalException(ErrorCode.CHAT_SEND_ROOM_NULL)