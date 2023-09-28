package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object ChatMessageNullException : GlobalException(ErrorCode.CHAT_MESSAGE_NULL)