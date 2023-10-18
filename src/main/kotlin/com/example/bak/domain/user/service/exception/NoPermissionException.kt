package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.CustomException

object NoPermissionException : CustomException(ErrorCode.NEED_ROLE)