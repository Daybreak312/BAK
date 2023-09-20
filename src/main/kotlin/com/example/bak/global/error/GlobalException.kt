package com.example.bak.global.error

open class GlobalException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)