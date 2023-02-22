package io.androkage.kontakt.kontaktapp.error

import io.ktor.http.*

sealed interface ApiError {
    val message: String
    val httpStatusCode: HttpStatusCode

    data class BadRequestError(override val message: String) : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.BadRequest
    }

    data class UnauthenticatedError(override val message: String = "You are currently not authenticated!") : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.Unauthorized
    }

    data class InternalServerError(override val message: String) : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.InternalServerError
    }

    data class NotFoundError(override val message: String) : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.NotFound
    }

    data class ConflictRecordError(override val message: String) : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.Conflict
    }

    data class GenericError(override val message: String) : ApiError {
        override val httpStatusCode: HttpStatusCode
            get() = HttpStatusCode.BadRequest
    }
}