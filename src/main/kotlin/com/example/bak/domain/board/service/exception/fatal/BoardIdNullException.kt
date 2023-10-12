package com.example.bak.domain.board.service.exception.fatal

import com.example.bak.global.error.FatalException
import com.example.bak.global.error.FatalErrorCode

object BoardIdNullException : FatalException(FatalErrorCode.BOARD_ID_NULL)