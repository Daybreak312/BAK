package com.example.bak.domain.user.service.exception

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException

object NoPermissionException : GlobalException(ErrorCode.NO_PERMISSION) {
}