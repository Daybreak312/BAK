package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.CustomException

object ChatRoomNullException : CustomException(ErrorCode.CHAT_ROOM_NULL)