package com.example.bak.domain.board.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object BoardNotFoundException : GlobalException(ErrorCode.BOARD_NOT_FOUND)