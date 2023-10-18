package com.example.bak.global.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object InvalidTokenException : CustomException(ErrorCode.INVALID_TOKEN)