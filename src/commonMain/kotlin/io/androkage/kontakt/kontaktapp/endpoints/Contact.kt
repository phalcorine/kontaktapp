package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.dto.*
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.kvision.annotations.KVService

@KVService
interface IContactEndpointService {
    suspend fun list(): Result<ApiDataResource<List<ContactDto>>>
    suspend fun findByUid(uid: String): Result<ApiDataResource<ContactDto>>
    suspend fun create(data: CreateContactDto): Result<ApiDataResource<EntityUid>>
    suspend fun update(uid: String, data: UpdateContactDto): Result<ApiDataResource<EntityUid>>
    suspend fun delete(uid: String): Result<ApiDataResource<EntityUid>>
}