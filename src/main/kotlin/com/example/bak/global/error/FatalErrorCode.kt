package com.example.bak.global.error

import org.springframework.http.HttpStatus

enum class FatalErrorCode(
    val status: HttpStatus,
    val message: String
) {

    BOARD_ID_NULL(HttpStatus.NOT_FOUND, "게시글의 id가 비어있습니다.")
}