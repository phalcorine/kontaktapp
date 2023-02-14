package io.androkage.kontakt.kontaktapp.endpoints

import io.androkage.kontakt.kontaktapp.domain.DomainError
import io.androkage.kontakt.kontaktapp.error.ApiError

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