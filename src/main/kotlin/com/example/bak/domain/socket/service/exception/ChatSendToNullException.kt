package com.example.bak.domain.socket.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object ChatSendToNullException : GlobalException(ErrorCode.CHAT_SENDTO_NULL)