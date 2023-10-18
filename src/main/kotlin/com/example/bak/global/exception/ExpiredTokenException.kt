package com.example.bak.global.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object ExpiredTokenException : CustomException(ErrorCode.EXPIRED_TOKEN)