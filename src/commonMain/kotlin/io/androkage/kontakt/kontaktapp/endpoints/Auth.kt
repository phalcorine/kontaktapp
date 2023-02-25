package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.dto.ApiSuccessResponse
import io.androkage.kontakt.kontaktapp.dto.AuthLoginRequestDto
import io.androkage.kontakt.kontaktapp.dto.AuthLoginResponseDto
import io.androkage.kontakt.kontaktapp.dto.AuthRegisterRequestDto
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.kvision.annotations.KVService

@KVService
interface IAuthEndpointService {
    suspend fun login(data: AuthLoginRequestDto): Result<AuthLoginResponseDto>
    suspend fun signup(data: AuthRegisterRequestDto): Result<ApiSuccessResponse>
}