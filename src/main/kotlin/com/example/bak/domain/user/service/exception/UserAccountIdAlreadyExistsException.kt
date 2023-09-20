package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.ErrorCode

object UserAccountIdAlreadyExistsException : GlobalException(ErrorCode.ACCOUNT_ID_ALREADY_EXISTS)
