package com.example.bak.global.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object ExpiredTokenException : GlobalException(ErrorCode.EXPIRED_TOKEN)