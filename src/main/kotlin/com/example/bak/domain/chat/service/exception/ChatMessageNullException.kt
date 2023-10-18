package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.CustomException

object ChatMessageNullException : CustomException(ErrorCode.CHAT_MESSAGE_NULL)