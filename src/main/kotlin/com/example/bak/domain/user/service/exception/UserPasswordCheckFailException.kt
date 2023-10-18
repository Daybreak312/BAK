package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object UserPasswordCheckFailException : CustomException(ErrorCode.USER_PASSWORD_CHECK_FAIL)