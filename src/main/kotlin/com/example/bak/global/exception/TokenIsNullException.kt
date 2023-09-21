package com.example.bak.global.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object TokenIsNullException : GlobalException(ErrorCode.TOKEN_IS_NULL)