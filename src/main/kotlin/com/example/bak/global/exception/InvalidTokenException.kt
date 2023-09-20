package com.example.bak.global.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object InvalidTokenException : GlobalException(ErrorCode.INVALID_TOKEN)