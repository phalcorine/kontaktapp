package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import arrow.core.continuations.EagerEffectScope
import arrow.core.continuations.either
import arrow.core.continuations.ensureNotNull
import io.androkage.kontakt.kontaktapp.domain.DomainError
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.androkage.kontakt.kontaktapp.plugins.JWT_CLAIM_PRINCIPAL
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun DomainError.toApiError(): ApiError = when (this) {
    is DomainError.DatabaseError -> {
        ApiError.InternalServerError(this.message)
    }
    is DomainError.EntityCreationFailureError -> {
        ApiError.InternalServerError(this.message)
    }
    is DomainError.EntityNotFoundError -> {
        ApiError.NotFoundError(this.message)
    }
}

suspend fun <RESP> ApplicationCall.asAuthenticated(block: suspend (String) -> RESP): RESP {
    val principal = principal<JWTPrincipal>() ?: throw IllegalStateException("Not Authenticated!")
    val userUid = principal[JWT_CLAIM_PRINCIPAL] ?: throw IllegalStateException("Not Authenticated!")

    return block(userUid)
}

suspend fun <RESP> ApplicationCall.asAuthenticatedEither(block: suspend (String) -> Either<ApiError, RESP>): Either<ApiError, RESP>  = either {
    val maybePrincipal = principal<JWTPrincipal>()
    val principal = ensureNotNull(maybePrincipal) {
        return@ensureNotNull ApiError.UnauthenticatedError()
    }
    val userId = ensureNotNull(principal[JWT_CLAIM_PRINCIPAL]) {
        return@ensureNotNull ApiError.UnauthenticatedError()
    }

    block(userId).bind()
}