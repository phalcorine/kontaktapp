package io.androkage.kontakt.kontaktapp.endpoints

import arrow.core.Either
import arrow.core.continuations.either
import io.androkage.kontakt.kontaktapp.data.facades.ContactEmailFacade
import io.androkage.kontakt.kontaktapp.data.facades.ContactFacade
import io.androkage.kontakt.kontaktapp.data.facades.ContactPhoneNumberFacade
import io.androkage.kontakt.kontaktapp.dto.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.kvision.remote.ServiceException

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class ContactEndpointService(
    private val contactFacade: ContactFacade,
    private val contactPhoneNumberFacade: ContactPhoneNumberFacade,
    private val contactEmailFacade: ContactEmailFacade,
    private val applicationCall: ApplicationCall
) : IContactEndpointService {
    override suspend fun list(): Result<ApiDataResource<List<ContactDto>>> {
        return either {
            applicationCall.asAuthenticatedEither { userUid ->
                val contacts = contactFacade
                    .list(userUid)
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

                Either.Right(ApiDataResource(contacts))
            }.bind()
        }.fold({
            Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }

    override suspend fun findByUid(uid: String): Result<ApiDataResource<ContactDto>> {
        return either {
            applicationCall.asAuthenticatedEither { userUid ->
                val contact = contactFacade
                    .findByUid(userUid, uid)
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

                Either.Right(ApiDataResource(loadedContact))
            }.bind()
        }.fold({
            Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }

    override suspend fun create(data: CreateContactDto): Result<ApiDataResource<EntityUid>> {
        return either {
            applicationCall.asAuthenticatedEither { userUid ->
                val entityUid = contactFacade
                    .create(userUid, data)
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

                Either.Right(ApiDataResource(entityUid))
            }.bind()
        }.fold({
            Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }

    override suspend fun update(uid: String, data: UpdateContactDto): Result<ApiDataResource<EntityUid>> {
        return either {
            applicationCall.asAuthenticatedEither { userUid ->
                val entityUid = contactFacade
                    .update(userUid, uid, data)
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

                Either.Right(ApiDataResource(entityUid))
            }.bind()
        }.fold({
           Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }

    override suspend fun delete(uid: String): Result<ApiDataResource<EntityUid>> {
        return either {
            applicationCall.asAuthenticatedEither { userUid ->
                val entityUid = contactFacade
                    .delete(userUid, uid)
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

                Either.Right(ApiDataResource(entityUid))
            }.bind()
        }.fold({
           Result.failure(ServiceException(it.message))
        }, {
            Result.success(it)
        })
    }
}