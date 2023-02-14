package io.androkage.kontakt.kontaktapp.api.endpoints

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.api.apiUrl
import io.androkage.kontakt.kontaktapp.api.httpClient
import io.androkage.kontakt.kontaktapp.dto.*
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object ContactEndpointServiceImpl : ContactEndpointService {
    override suspend fun list(): Either<ApiError, ApiDataResource<List<ContactDto>>> {
        return Either.catch {
            val apiResponse: ApiDataResource<List<ContactDto>> = httpClient.get {
                url("$apiUrl/contacts/list")
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }.body()
            apiResponse
        }.mapLeft {
            ApiError.GenericError(it.message!!)
        }
    }

    override suspend fun findByUid(uid: String): Either<ApiError, ApiDataResource<ContactDto>> {
        return Either.catch {
            val apiResponse: ApiDataResource<ContactDto> = httpClient.get {
                url("$apiUrl/contacts/find/uid/$uid")
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }.body()
            apiResponse
        }.mapLeft {
            ApiError.GenericError(it.message!!)
        }
    }

    override suspend fun create(data: CreateContactDto): Either<ApiError, ApiDataResource<EntityUid>> {
        return Either.catch {
            val apiResponse: ApiDataResource<EntityUid> = httpClient.post {
                url("$apiUrl/contacts/create")
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            apiResponse
        }.mapLeft {
            ApiError.GenericError(it.message!!)
        }
    }

    override suspend fun update(uid: String, data: UpdateContactDto): Either<ApiError, ApiDataResource<EntityUid>> {
        return Either.catch {
            val apiResponse: ApiDataResource<EntityUid> = httpClient.patch {
                url("$apiUrl/contacts/update/$uid")
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            apiResponse
        }.mapLeft {
            ApiError.GenericError(it.message!!)
        }
    }

    override suspend fun delete(uid: String): Either<ApiError, ApiDataResource<EntityUid>> {
        return Either.catch {
            val apiResponse: ApiDataResource<EntityUid> = httpClient.delete {
                url("$apiUrl/contacts/delete/$uid")
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }.body()
            apiResponse
        }.mapLeft {
            ApiError.GenericError(it.message!!)
        }
    }
}