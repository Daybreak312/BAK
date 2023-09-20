package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object UserPasswordMismatchException : GlobalException(ErrorCode.USER_PASSWORD_MISMATCH)