package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import arrow.core.continuations.either
import io.androkage.kontakt.kontaktapp.dto.ApiSuccessResponse
import io.ktor.server.application.*
import io.kvision.remote.ServiceException

@Suppress("ACTUAL_WITH_EXPECT")
actual class AuthCheckerEndpointService(
    private val applicationCall: ApplicationCall
) : IAuthCheckerEndpointService {
    override suspend fun checkAuth(): Result<ApiSuccessResponse> {
        return either {
            applicationCall.asAuthenticatedEither {
                Either.Right(ApiSuccessResponse("Authentication Successful!"))
            }.bind()
        }.fold({
            Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }
}