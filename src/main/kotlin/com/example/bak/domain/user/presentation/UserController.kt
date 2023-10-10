package com.example.bak.domain.user.presentation

import com.example.bak.domain.user.presentation.dto.TokenResponse
import com.example.bak.domain.user.presentation.dto.request.ReissueRequest
import com.example.bak.domain.user.presentation.dto.request.UserLoginRequest
import com.example.bak.domain.user.presentation.dto.request.UserSignRequest
import com.example.bak.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/sign")
    @ResponseStatus(HttpStatus.CREATED)
    fun sign(@RequestBody request: UserSignRequest) {
        userService.sign(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): TokenResponse {
        return userService.login(request)
    }

    @PostMapping("/reissue")
    fun reissue(@RequestBody request: ReissueRequest): TokenResponse {
        return userService.reissue(request)
    }
}