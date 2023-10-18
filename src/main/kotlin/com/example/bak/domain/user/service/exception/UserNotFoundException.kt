package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object UserNotFoundException : CustomException(ErrorCode.USER_NOT_FOUND)