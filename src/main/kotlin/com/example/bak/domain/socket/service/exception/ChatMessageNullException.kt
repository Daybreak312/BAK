package com.example.bak.domain.socket.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object ChatMessageNullException : GlobalException(ErrorCode.CHAT_MESSAGE_NULL)