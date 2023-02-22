package io.androkage.kontakt.kontaktapp.routes

import io.androkage.kontakt.kontaktapp.dto.ApiErrorResponse
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.handleError(error: ApiError): Unit = when (error) {
    is ApiError.InternalServerError -> {
        call.respond(
            status = HttpStatusCode.InternalServerError,
            ApiErrorResponse(error.message)
        )
    }
    is ApiError.UnauthenticatedError -> {
        call.respond(
            status = error.httpStatusCode,
            ApiErrorResponse(error.message)
        )
    }
    is ApiError.BadRequestError -> {
        call.respond(
            status = error.httpStatusCode,
            ApiErrorResponse(error.message)
        )
    }
    is ApiError.NotFoundError -> {
        call.respond(
            status = error.httpStatusCode,
            ApiErrorResponse(error.message)
        )
    }
    is ApiError.GenericError -> {
        call.respond(
            status = error.httpStatusCode,
            ApiErrorResponse(error.message)
        )
    }
    is ApiError.ConflictRecordError -> {
        call.respond(
            status = error.httpStatusCode,
            ApiErrorResponse(error.message)
        )
    }
}