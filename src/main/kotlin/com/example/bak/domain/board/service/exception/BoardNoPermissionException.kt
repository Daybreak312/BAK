package com.example.bak.domain.board.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object BoardNoPermissionException : GlobalException(ErrorCode.BOARD_NO_PERMISSION)