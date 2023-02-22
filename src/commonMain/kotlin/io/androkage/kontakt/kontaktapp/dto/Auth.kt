package io.androkage.kontakt.kontaktapp.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class AuthLoginResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    val user: AuthLoggedInUserDto
)

@Serializable
data class AuthLoggedInUserDto(
    @SerialName("full_name")
    val fullName: String
)

@Serializable
data class AuthRegisterRequestDto(
    @SerialName("full_name")
    val fullName: String,
    val email: String,
    val password: String
)