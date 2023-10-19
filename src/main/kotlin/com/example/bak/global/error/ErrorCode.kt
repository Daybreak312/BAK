package com.example.bak.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    USER_PASSWORD_CHECK_FAIL(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    ACCOUNT_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "사용하려는 아이디가 이미 존재합니다."),
    NEED_ROLE(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // TOKEN
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    // BOARD
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    BOARD_NO_PERMISSION(HttpStatus.FORBIDDEN, "게시글을 수정할 권한이 없습니다."),

    // CHAT
    CHAT_MESSAGE_NULL(HttpStatus.BAD_REQUEST, "채팅 내용이 비어있습니다."),
    CHAT_ROOM_NULL(HttpStatus.BAD_REQUEST, "채팅을 전달할 대상이 필요합니다."),
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_ROOM_NO_PERMISSION(HttpStatus.FORBIDDEN, "이 채팅방에 메세지를 보낼 권한이 없습니다."),
    PARTICIPANTS_NOT_CONTAIN_GENERATOR(HttpStatus.BAD_REQUEST, "채팅방 참여자에는 본인이 포함되어야 합니다."),

    // SERVER
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 처리되지 않은 오류입니다.")
}