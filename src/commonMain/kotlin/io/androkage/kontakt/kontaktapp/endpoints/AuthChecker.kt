package io.androkage.kontakt.kontaktapp.endpoints

import io.androkage.kontakt.kontaktapp.dto.ApiSuccessResponse
import io.kvision.annotations.KVService

@KVService
interface IAuthCheckerEndpointService {
    suspend fun checkAuth(): Result<ApiSuccessResponse>
}