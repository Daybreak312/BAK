package com.example.bak.domain.board.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.CustomException

object BoardNotFoundException : CustomException(ErrorCode.BOARD_NOT_FOUND)