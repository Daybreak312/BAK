package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.CustomException
import com.example.bak.global.error.ErrorCode

object UserAccountIdAlreadyExistsException : CustomException(ErrorCode.ACCOUNT_ID_ALREADY_EXISTS)
