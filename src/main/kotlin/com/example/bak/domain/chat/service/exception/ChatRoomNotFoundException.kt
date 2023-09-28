package com.example.bak.domain.chat.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object ChatRoomNotFoundException : GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND)