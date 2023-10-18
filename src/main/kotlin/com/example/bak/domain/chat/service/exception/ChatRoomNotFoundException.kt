package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.CustomException

object ChatRoomNotFoundException : CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND)