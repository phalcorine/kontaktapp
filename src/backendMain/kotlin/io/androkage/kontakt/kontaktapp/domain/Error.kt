package io.androkage.kontakt.kontaktapp.domain

sealed interface DomainError {
    val message: String

    data class DatabaseError(val throwable: Throwable) : DomainError {
        override val message: String
            get() = throwable.message ?: "A database error occurred!"
    }

    object EntityCreationFailureError : DomainError {
        override val message: String
            get() = "An error occurred while attempting to create a record!"

    }

    object EntityNotFoundError : DomainError {
        override val message: String
            get() = "A record with the specified criteria was not found!"
    }
}