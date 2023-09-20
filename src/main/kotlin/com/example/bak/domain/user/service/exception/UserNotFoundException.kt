package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object UserNotFoundException : GlobalException(ErrorCode.USER_NOT_FOUND)