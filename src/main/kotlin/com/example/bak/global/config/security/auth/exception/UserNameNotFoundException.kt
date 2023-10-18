package com.example.bak.global.config.security.auth.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object UserNameNotFoundException : CustomException(ErrorCode.USER_NOT_FOUND)