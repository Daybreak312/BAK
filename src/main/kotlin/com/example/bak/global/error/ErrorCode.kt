package com.example.bak.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 400
    USER_PASSWORD_CHECK_FAIL(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 403
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다."),
    NOT_BEARER_TOKEN(HttpStatus.FORBIDDEN, "Bearer JWT 토큰이 아닙니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰입니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),

    // 409
    ACCOUNT_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "사용하려는 아이디가 이미 존재합니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 처리되지 않은 오류입니다.")
}