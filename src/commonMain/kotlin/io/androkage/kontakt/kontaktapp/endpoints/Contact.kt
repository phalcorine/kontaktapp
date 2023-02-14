package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.dto.*
import io.androkage.kontakt.kontaktapp.error.ApiError

interface ContactEndpointService {
    suspend fun list(): Either<ApiError, ApiDataResource<List<ContactDto>>>
    suspend fun findByUid(uid: String): Either<ApiError, ApiDataResource<ContactDto>>
    suspend fun create(data: CreateContactDto): Either<ApiError, ApiDataResource<EntityUid>>
    suspend fun update(uid: String, data: UpdateContactDto): Either<ApiError, ApiDataResource<EntityUid>>
    suspend fun delete(uid: String): Either<ApiError, ApiDataResource<EntityUid>>
}