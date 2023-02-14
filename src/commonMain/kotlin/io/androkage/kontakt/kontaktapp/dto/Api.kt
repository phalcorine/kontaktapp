package io.androkage.kontakt.kontaktapp.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiDataResource<T>(
    val data: T
)

@Serializable
data class ApiErrorResponse(val message: String)