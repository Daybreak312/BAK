package com.example.bak.domain.user.service

import com.example.bak.domain.user.controller.dto.TokenResponse
import com.example.bak.domain.user.controller.dto.request.ReissueRequest
import com.example.bak.domain.user.controller.dto.request.UserLoginRequest
import com.example.bak.domain.user.controller.dto.request.UserSignRequest
import com.example.bak.domain.user.entity.User
import com.example.bak.domain.user.entity.role.Role
import com.example.bak.domain.user.repository.UserRepository
import com.example.bak.domain.user.service.exception.UserAccountIdAlreadyExistsException
import com.example.bak.domain.user.service.exception.UserNotFoundException
import com.example.bak.domain.user.service.exception.UserPasswordCheckFailException
import com.example.bak.domain.user.service.exception.UserPasswordMismatchException
import com.example.bak.global.config.security.jwt.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository,

    private val jwtTokenProvider: JwtTokenProvider,

    private val passwordEncoder: PasswordEncoder
) {

    fun sign(request: UserSignRequest) {

        with(request) {

            if (userRepository.existsByAccountId(accountId))
                throw UserAccountIdAlreadyExistsException

            if (password != passwordCheck)
                throw UserPasswordCheckFailException

            userRepository.save(
                User(
                    name = name,
                    accountId = accountId,
                    password = passwordEncoder.encode(password),
                    role = Role.USER
                )
            )
        }
    }

    fun login(request: UserLoginRequest): TokenResponse {

        return with(request) {

            val user: User = userRepository.findByAccountId(accountId) ?: throw UserNotFoundException

            if (!passwordEncoder.matches(password, user.password))
                throw UserPasswordMismatchException

            jwtTokenProvider.createToken(accountId)
        }
    }

    fun reissue(request: ReissueRequest): TokenResponse {
        return jwtTokenProvider.reissue(request.token)
    }
}