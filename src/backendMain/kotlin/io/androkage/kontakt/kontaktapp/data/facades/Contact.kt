package io.androkage.kontakt.kontaktapp.data.facades

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.data.DatabaseFactory.dbQuery
import io.androkage.kontakt.kontaktapp.data.entities.*
import io.androkage.kontakt.kontaktapp.domain.DomainError
import io.androkage.kontakt.kontaktapp.dto.*
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.UUID

interface ContactFacade {
    suspend fun list(userUid: String): Either<DomainError, List<ContactDto>>
    suspend fun findByUid(userUid: String, contactUid: String): Either<DomainError, ContactDto>
    suspend fun create(userUid: String, data: CreateContactDto): Either<DomainError, EntityUid>
    suspend fun update(userUid: String, contactUid: String, data: UpdateContactDto): Either<DomainError, EntityUid>
    suspend fun delete(userUid: String, contactUid: String): Either<DomainError, EntityUid>
}

// Database

object ContactFacadeDatabaseImpl : ContactFacade {
    override suspend fun list(userUid: String): Either<DomainError, List<ContactDto>> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .select { ContactTable.userUid eq userUid }
                    .map { it.toContactDto() }
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByUid(userUid: String, contactUid: String): Either<DomainError, ContactDto> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .select { ContactTable.uid eq contactUid and (ContactTable.userUid eq userUid) }
                    .singleOrNull()
                    ?.toContactDto()
            } ?: return Either.Left(DomainError.EntityNotFoundError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun create(userUid: String, data: CreateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .insert {
                        val now = LocalDateTime.now()
                        it[uid] = UUID.randomUUID().toString()
                        it[name] = data.name
                        it[note] = data.note
                        it[createdAt] = now.toKotlinLocalDateTime()
                        it[updatedAt] = now.toKotlinLocalDateTime()
                        it[ContactTable.userUid] = userUid
                    }.resultedValues?.singleOrNull()?.toContactDto()?.let {
                        EntityUid(it.uid)
                    }
            } ?: return Either.Left(DomainError.EntityCreationFailureError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun update(userUid: String, contactUid: String, data: UpdateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                val hasUpdated = ContactTable
                    .update({ ContactTable.uid eq contactUid and (ContactTable.userUid eq userUid) }) {
                        val now = LocalDateTime.now()
                        it[name] = data.name
                        it[note] = data.note
                        it[updatedAt] = now.toKotlinLocalDateTime()
                    } > 0

                EntityUid(contactUid)
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }

    }

    override suspend fun delete(userUid: String, contactUid: String): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                val hasDeleted = ContactTable
                    .deleteWhere { uid eq contactUid and (ContactTable.userUid eq userUid) } > 0

                EntityUid(contactUid)
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }
}

// In Memory

object ContactFacadeInMemoryImpl : ContactFacade {
    private var contactStore: MutableList<ContactDto> = mutableListOf()

    override suspend fun list(userUid: String): Either<DomainError, List<ContactDto>> {
        val contacts = contactStore
            .filter { it.userUid == userUid }
            .toList()
        return Either.Right(contacts)
    }

    override suspend fun findByUid(userUid: String, contactUid: String): Either<DomainError, ContactDto> {
        val contact = contactStore
            .firstOrNull { it.uid == contactUid && it.userUid == userUid }
            ?: return Either.Left(DomainError.EntityNotFoundError)

        return Either.Right(contact)
    }

    override suspend fun create(userUid: String, data: CreateContactDto): Either<DomainError, EntityUid> {
        val now = LocalDateTime.now()
        val contact = ContactDto(
            uid = UUID.randomUUID().toString(),
            name = data.name,
            note = data.note,
            createdAt = now.toKotlinLocalDateTime(),
            updatedAt = now.toKotlinLocalDateTime(),
            userUid = userUid
        )
        contactStore.add(contact)

        return Either.Right(EntityUid(contact.uid))
    }

    override suspend fun update(userUid: String, contactUid: String, data: UpdateContactDto): Either<DomainError, EntityUid> {
        contactStore = contactStore
            .map { contact ->
                val now = LocalDateTime.now()
                if (contact.uid != contactUid && contact.userUid != userUid) {
                    return@map contact
                }

                contact.copy(
                    name = data.name,
                    note = data.note,
                    updatedAt = now.toKotlinLocalDateTime()
                )
            }.toMutableList()

        return Either.Right(EntityUid(contactUid))
    }

    override suspend fun delete(userUid: String, contactUid: String): Either<DomainError, EntityUid> {
        contactStore
            .removeIf { it.uid == contactUid && it.userUid == userUid }

        return Either.Right(EntityUid(contactUid))
    }
}