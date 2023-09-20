package com.example.bak.global.config.security.auth.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object UserNameNotFoundException : GlobalException(ErrorCode.USER_NOT_FOUND)