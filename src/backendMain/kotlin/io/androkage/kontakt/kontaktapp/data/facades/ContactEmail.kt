package io.androkage.kontakt.kontaktapp.data.facades

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.data.DatabaseFactory.dbQuery
import io.androkage.kontakt.kontaktapp.data.entities.ContactEmailTable
import io.androkage.kontakt.kontaktapp.data.entities.toContactEmailDto
import io.androkage.kontakt.kontaktapp.domain.DomainError
import io.androkage.kontakt.kontaktapp.dto.ContactEmailDto
import io.androkage.kontakt.kontaktapp.dto.UpdateContactEmailDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select

interface ContactEmailFacade {
    suspend fun listByContactUid(contactUid: String): Either<DomainError, List<ContactEmailDto>>
    suspend fun insertByContactUid(contactUid: String, data: List<UpdateContactEmailDto>): Either<DomainError, Unit>
    suspend fun deleteByContactUid(contactUid: String): Either<DomainError, Unit>
}

object ContactEmailFacadeDatabaseImpl : ContactEmailFacade {
    override suspend fun listByContactUid(contactUid: String): Either<DomainError, List<ContactEmailDto>> {
        return Either.catch {
            dbQuery {
                ContactEmailTable
                    .select { ContactEmailTable.contact_uid eq contactUid }
                    .map { it.toContactEmailDto() }
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun insertByContactUid(
        contactUid: String,
        data: List<UpdateContactEmailDto>
    ): Either<DomainError, Unit> {
        return Either.catch {
            dbQuery {
                ContactEmailTable
                    .batchInsert(data) {
                        this[ContactEmailTable.email] = it.email
                        this[ContactEmailTable.contact_uid] = contactUid
                    }
            }

            Unit
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun deleteByContactUid(contactUid: String): Either<DomainError, Unit> {
        return Either.catch {
            dbQuery {
                ContactEmailTable
                    .deleteWhere { ContactEmailTable.contact_uid eq contactUid }
            }

            Unit
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }
}

object ContactEmailFacadeInMemoryImpl : ContactEmailFacade {
    private val contactEmailStore: MutableList<ContactEmailDto> = mutableListOf()

    override suspend fun listByContactUid(contactUid: String): Either<DomainError, List<ContactEmailDto>> {
        val contactEmails = contactEmailStore
            .filter { it.contactUid == contactUid }

        return Either.Right(contactEmails)
    }

    override suspend fun insertByContactUid(
        contactUid: String,
        data: List<UpdateContactEmailDto>
    ): Either<DomainError, Unit> {
        contactEmailStore
            .addAll(data.map { dto ->
                ContactEmailDto(
                    email = dto.email,
                    contactUid = contactUid
                )
            })

        return Either.Right(Unit)
    }

    override suspend fun deleteByContactUid(contactUid: String): Either<DomainError, Unit> {
        contactEmailStore
            .removeIf { it.contactUid == contactUid }

        return Either.Right(Unit)
    }

}