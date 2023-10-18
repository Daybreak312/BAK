package com.example.bak.global.error.handler

import com.example.bak.global.error.ErrorCode
import com.example.bak.global.error.FatalException
import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.dto.ErrorResponse
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(GlobalException::class)
    fun globalExceptionHandler(e: GlobalException) =
        ResponseEntity
            .status(e.errorCode.status)
            .body(ErrorResponse(e.errorCode.status.value(), e.errorCode.message))

    @ExceptionHandler(FatalException::class)
    fun fatalExceptionHandler(e: FatalException): ResponseEntity<ErrorResponse> {

        LogFactory.getLog(javaClass).fatal(e.errorCode.message)

        return ResponseEntity
            .status(e.errorCode.status)
            .body(ErrorResponse(e.errorCode.status.value(), e.errorCode.message))
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception) =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.status.value(), "FATAL : " + e.message))
}