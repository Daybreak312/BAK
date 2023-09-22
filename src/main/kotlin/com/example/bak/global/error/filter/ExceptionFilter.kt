package com.example.bak.global.error.filter

import com.example.bak.global.error.GlobalException
import com.example.bak.global.error.dto.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: GlobalException) {
            response.run {
                status = e.errorCode.status.value()
                contentType = MediaType.APPLICATION_JSON_VALUE
                characterEncoding = "UTF-8"
            }
            objectMapper.writeValue(
                response.writer,
                ErrorResponse(
                    e.errorCode.status.value(),
                    e.errorCode.message
                )
            )
        }
    }
}