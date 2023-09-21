package com.example.bak.global.error.handler

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(GlobalException::class)
    fun globalExceptionHandler(e: GlobalException) = ResponseEntity
        .status(e.errorCode.status)
        .body(ErrorResponse(e.errorCode.status.value(), e.errorCode.message))

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception) = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.status.value(), e.message))
}