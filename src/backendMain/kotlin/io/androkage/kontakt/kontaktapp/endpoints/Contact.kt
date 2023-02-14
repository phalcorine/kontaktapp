package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import arrow.core.continuations.either
import io.androkage.kontakt.kontaktapp.data.facades.ContactEmailFacade
import io.androkage.kontakt.kontaktapp.data.facades.ContactFacade
import io.androkage.kontakt.kontaktapp.data.facades.ContactPhoneNumberFacade
import io.androkage.kontakt.kontaktapp.dto.*
import io.androkage.kontakt.kontaktapp.error.ApiError

class ContactEndpointServiceImpl(
    private val contactFacade: ContactFacade,
    private val contactPhoneNumberFacade: ContactPhoneNumberFacade,
    private val contactEmailFacade: ContactEmailFacade
) : ContactEndpointService {
    override suspend fun list(): Either<ApiError, ApiDataResource<List<ContactDto>>> = either {
        val contacts = contactFacade
            .list()
            .map {
                it.map { contact ->
                    val phoneNumbers = contactPhoneNumberFacade
                        .listByContactUid(contact.uid)
                        .mapLeft { error -> error.toApiError() }
                        .bind()

                    val emails = contactEmailFacade
                        .listByContactUid(contact.uid)
                        .mapLeft { error -> error.toApiError() }
                        .bind()

                    contact.copy(
                        phoneNumbers = phoneNumbers,
                        emails = emails
                    )
                }
            }
            .mapLeft { it.toApiError() }
            .bind()

        ApiDataResource(contacts)
    }

    override suspend fun findByUid(uid: String): Either<ApiError, ApiDataResource<ContactDto>> = either {
        val contact = contactFacade
            .findByUid(uid)
            .mapLeft { it.toApiError() }
            .bind()

        val phoneNumbers = contactPhoneNumberFacade
            .listByContactUid(contact.uid)
            .mapLeft { it.toApiError() }
            .bind()

        val emails = contactEmailFacade
            .listByContactUid(contact.uid)
            .mapLeft { it.toApiError() }
            .bind()

        val loadedContact = contact.copy(
            phoneNumbers = phoneNumbers,
            emails = emails
        )

        ApiDataResource(loadedContact)
    }

    override suspend fun create(data: CreateContactDto): Either<ApiError, ApiDataResource<EntityUid>> = either {
        val entityUid = contactFacade
            .create(data)
            .mapLeft{ it.toApiError() }
            .bind()

        contactPhoneNumberFacade
            .insertByContactUid(
                entityUid.uid,
                data.phoneNumbers.map { UpdateContactPhoneNumberDto(it) }
            )
            .mapLeft { it.toApiError() }
            .bind()

        contactEmailFacade
            .insertByContactUid(
                entityUid.uid,
                data.emails.map { UpdateContactEmailDto(it) }
            )
            .mapLeft { it.toApiError() }
            .bind()

        ApiDataResource(entityUid)
    }

    override suspend fun update(uid: String, data: UpdateContactDto): Either<ApiError, ApiDataResource<EntityUid>> = either {
        val entityUid = contactFacade
            .update(uid, data)
            .mapLeft { it.toApiError() }
            .bind()

        // Delete old phone numbers and email
        contactPhoneNumberFacade
            .deleteByContactUid(uid)
            .mapLeft { it.toApiError() }
            .bind()

        contactEmailFacade
            .deleteByContactUid(uid)
            .mapLeft { it.toApiError() }
            .bind()

        // Insert new phone numbers and email
        contactPhoneNumberFacade
            .insertByContactUid(
                entityUid.uid,
                data.phoneNumbers.map { UpdateContactPhoneNumberDto(it) }
            )
            .mapLeft { it.toApiError() }
            .bind()

        contactEmailFacade
            .insertByContactUid(
                entityUid.uid,
                data.emails.map { UpdateContactEmailDto(it) }
            )
            .mapLeft { it.toApiError() }
            .bind()

        ApiDataResource(entityUid)
    }

    override suspend fun delete(uid: String): Either<ApiError, ApiDataResource<EntityUid>> = either {
        val entityUid = contactFacade
            .delete(uid)
            .mapLeft { it.toApiError() }
            .bind()

        contactPhoneNumberFacade
            .deleteByContactUid(entityUid.uid)
            .mapLeft { it.toApiError() }
            .bind()

        contactEmailFacade
            .deleteByContactUid(entityUid.uid)
            .mapLeft { it.toApiError() }
            .bind()

        ApiDataResource(entityUid)
    }
}