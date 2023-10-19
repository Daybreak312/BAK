package com.example.bak.domain.user.service

import com.example.bak.domain.user.entity.User
import com.example.bak.domain.user.entity.role.Role
import com.example.bak.domain.user.persistence.UserRepository
import com.example.bak.domain.user.presentation.dto.TokenResponse
import com.example.bak.domain.user.presentation.dto.request.ReissueRequest
import com.example.bak.domain.user.presentation.dto.request.UserLoginRequest
import com.example.bak.domain.user.presentation.dto.request.UserSignRequest
import com.example.bak.domain.user.service.exception.UserAccountIdAlreadyExistsException
import com.example.bak.domain.user.service.exception.UserNotFoundException
import com.example.bak.domain.user.service.exception.UserPasswordCheckFailException
import com.example.bak.domain.user.service.exception.UserPasswordMismatchException
import com.example.bak.global.config.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository,

    private val jwtTokenProvider: JwtTokenProvider,

    private val passwordEncoder: PasswordEncoder
) {

    fun sign(request: UserSignRequest) {

        request.run {

            if (userRepository.existsByAccountId(accountId)) throw UserAccountIdAlreadyExistsException

            if (password != passwordCheck) throw UserPasswordCheckFailException

            userRepository.save(
                User(
                    accountId = accountId,
                    name = name,
                    password = passwordEncoder.encode(password),
                    role = Role.USER
                )
            )
        }
    }

    fun login(request: UserLoginRequest): TokenResponse {

        return request.run {

            val user = userRepository.findByAccountId(accountId) ?: throw UserNotFoundException

            if (!passwordEncoder.matches(password, user.password)) throw UserPasswordMismatchException

            jwtTokenProvider.createToken(accountId)
        }
    }

    fun reissue(request: ReissueRequest): TokenResponse =
        jwtTokenProvider.reissue(request.token)
}