package io.androkage.kontakt.kontaktapp.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    val uid: String,
    val name: String,
    val note: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val phoneNumbers: List<ContactPhoneNumberDto> = emptyList(),
    val emails: List<ContactEmailDto> = emptyList(),
    // relations
    val userUid: String,
)

@Serializable
data class ContactPhoneNumberDto(
    val phoneNumber: String,
    val contactUid: String
)

@Serializable
data class ContactEmailDto(
    val email: String,
    val contactUid: String
)

@Serializable
data class CreateContactDto(
    val name: String,
    val phoneNumbers: List<String>,
    val emails: List<String>,
    val note: String? = null
)

@Serializable
data class UpdateContactDto(
    val name: String,
    val phoneNumbers: List<String>,
    val emails: List<String>,
    val note: String? = null
)

@Serializable
data class UpdateContactPhoneNumberDto(
    val phoneNumber: String
)

@Serializable
data class UpdateContactEmailDto(
    val email: String
)