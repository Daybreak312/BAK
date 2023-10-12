package com.example.bak.global.error

open class FatalException(
    val errorCode: FatalErrorCode
) : RuntimeException(errorCode.message)