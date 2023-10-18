package com.example.bak.global.error

open class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)