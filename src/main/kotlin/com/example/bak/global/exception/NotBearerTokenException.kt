package com.example.bak.global.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object NotBearerTokenException : GlobalException(ErrorCode.NOT_BEARER_TOKEN)