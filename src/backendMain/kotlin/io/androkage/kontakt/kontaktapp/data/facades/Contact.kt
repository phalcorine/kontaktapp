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
    suspend fun list(): Either<DomainError, List<ContactDto>>
    suspend fun findByUid(uid: String): Either<DomainError, ContactDto>
    suspend fun create(data: CreateContactDto): Either<DomainError, EntityUid>
    suspend fun update(uid: String, data: UpdateContactDto): Either<DomainError, EntityUid>
    suspend fun delete(uid: String): Either<DomainError, EntityUid>
}

// Database

object ContactFacadeDatabaseImpl : ContactFacade {
    override suspend fun list(): Either<DomainError, List<ContactDto>> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .selectAll()
                    .map { it.toContactDto() }
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByUid(uid: String): Either<DomainError, ContactDto> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .select { ContactTable.uid eq uid }
                    .singleOrNull()
                    ?.toContactDto()
            } ?: return Either.Left(DomainError.EntityNotFoundError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun create(data: CreateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                ContactTable
                    .insert {
                        val now = LocalDateTime.now()
                        it[uid] = UUID.randomUUID().toString()
                        it[name] = data.name
                        it[note] = data.note
                        it[created_at] = now.toKotlinLocalDateTime()
                        it[updated_at] = now.toKotlinLocalDateTime()
                    }.resultedValues?.singleOrNull()?.toContactDto()?.let {
                        EntityUid(it.uid)
                    }
            } ?: return Either.Left(DomainError.EntityCreationFailureError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun update(uid: String, data: UpdateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                val hasUpdated = ContactTable
                    .update({ ContactTable.uid eq uid }) {
                        val now = LocalDateTime.now()
                        it[name] = data.name
                        it[note] = data.note
                        it[updated_at] = now.toKotlinLocalDateTime()
                    } > 0
                EntityUid(uid)
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }

    }

    override suspend fun delete(uid: String): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                val hasDeleted = ContactTable
                    .deleteWhere { ContactTable.uid eq uid } > 0
                EntityUid(uid)
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }
}

// In Memory

object ContactFacadeInMemoryImpl : ContactFacade {
    private var contactStore: MutableList<ContactDto> = mutableListOf()

    override suspend fun list(): Either<DomainError, List<ContactDto>> {
        return Either.catch {
            contactStore
                .toList()
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByUid(uid: String): Either<DomainError, ContactDto> {
        return Either.catch {
            contactStore
                .firstOrNull { it.uid == uid }
                ?: return Either.Left(DomainError.EntityNotFoundError)
            }
            .mapLeft {
                DomainError.DatabaseError(it)
            }
    }

    override suspend fun create(data: CreateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
            val now = LocalDateTime.now()
            val contact = ContactDto(
                uid = UUID.randomUUID().toString(),
                name = data.name,
                note = data.note,
                createdAt = now.toKotlinLocalDateTime(),
                updatedAt = now.toKotlinLocalDateTime()
            )
            contactStore.add(contact)
            EntityUid(contact.uid)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun update(uid: String, data: UpdateContactDto): Either<DomainError, EntityUid> {
        return Either.catch {
                contactStore = contactStore
                    .map { contact ->
                        val now = LocalDateTime.now()
                        if (contact.uid != uid) {
                            return@map contact
                        }

                        contact.copy(
                            name = data.name,
                            note = data.note,
                            updatedAt = now.toKotlinLocalDateTime()
                        )
                    }.toMutableList()
                EntityUid(uid)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }

    }

    override suspend fun delete(uid: String): Either<DomainError, EntityUid> {
        return Either.catch {
                contactStore
                    .removeIf { it.uid != uid }
                EntityUid(uid)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }
}