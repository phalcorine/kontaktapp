package io.androkage.kontakt.kontaktapp.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

data class UserDto(
    val uid: String,
    val fullName: String,
    val email: String,
    val passwordHash: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class UserWithoutPasswordDto(
    val uid: String,
    val fullName: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class CreateUserDto(
    val fullName: String,
    val email: String,
    val passwordHash: String
)

data class UpdateUserDto(
    val fullName: String,
    val email: String,
    val passwordHash: String
)