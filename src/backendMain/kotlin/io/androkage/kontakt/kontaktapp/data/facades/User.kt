package io.androkage.kontakt.kontaktapp.data.facades

import arrow.core.Either
import io.androkage.kontakt.kontaktapp.data.DatabaseFactory.dbQuery
import io.androkage.kontakt.kontaktapp.data.entities.UserTable
import io.androkage.kontakt.kontaktapp.data.entities.toUserDto
import io.androkage.kontakt.kontaktapp.domain.DomainError
import io.androkage.kontakt.kontaktapp.dto.*
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.UUID

interface UserFacade {
    suspend fun list(): Either<DomainError, List<UserDto>>
    suspend fun findByUid(uid: String): Either<DomainError, UserDto>
    suspend fun findByEmail(email: String): Either<DomainError, UserDto>
    suspend fun findByEmailOrNull(email: String): Either<DomainError, UserDto?>
    suspend fun create(data: CreateUserDto): Either<DomainError, EntityUid>
    suspend fun update(uid: String, data: UpdateUserDto): Either<DomainError, EntityUid>
    suspend fun delete(uid: String): Either<DomainError, EntityUid>
}

object UserFacadeDatabaseImpl : UserFacade {
    override suspend fun list(): Either<DomainError, List<UserDto>> {
        return Either.catch {
            dbQuery {
                UserTable
                    .selectAll()
                    .map { it.toUserDto() }
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByUid(uid: String): Either<DomainError, UserDto> {
        return Either.catch {
            dbQuery {
                UserTable
                    .select { UserTable.uid eq uid }
                    .singleOrNull()
                    ?.toUserDto()
            } ?: return Either.Left(DomainError.EntityNotFoundError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByEmail(email: String): Either<DomainError, UserDto> {
        return Either.catch {
            dbQuery {
                UserTable
                    .select { UserTable.email eq email }
                    .singleOrNull()
                    ?.toUserDto()
            } ?: return Either.Left(DomainError.EntityNotFoundError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun findByEmailOrNull(email: String): Either<DomainError, UserDto?> {
        return Either.catch {
            dbQuery {
                UserTable
                    .select { UserTable.email eq email }
                    .singleOrNull()
                    ?.toUserDto()
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun create(data: CreateUserDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                UserTable
                    .insert {
                        val now = LocalDateTime.now()

                        it[uid] = UUID.randomUUID().toString()
                        it[fullName] = data.fullName
                        it[email] = data.email
                        it[passwordHash] = data.passwordHash
                        it[createdAt] = now.toKotlinLocalDateTime()
                        it[updatedAt] = now.toKotlinLocalDateTime()
                    }.resultedValues?.singleOrNull()?.toUserDto()?.let {
                        EntityUid(it.uid)
                    }
            } ?: return Either.Left(DomainError.EntityCreationFailureError)
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }

    override suspend fun update(uid: String, data: UpdateUserDto): Either<DomainError, EntityUid> {
        return Either.catch {
            dbQuery {
                val hasUpdated = UserTable
                    .update({ UserTable.uid eq uid }) {
                        val now = LocalDateTime.now()

                        it[fullName] = data.fullName
                        it[email] = data.email
                        it[passwordHash] = data.passwordHash
                        it[updatedAt] = now.toKotlinLocalDateTime()
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
                val hasDeleted = UserTable
                    .deleteWhere { UserTable.uid eq uid } > 0

                EntityUid(uid)
            }
        }.mapLeft {
            DomainError.DatabaseError(it)
        }
    }
}

object UserFacadeInMemoryImpl : UserFacade {
    private var userStore: MutableList<UserDto> = mutableListOf()
    override suspend fun list(): Either<DomainError, List<UserDto>> {
        val users = userStore
            .toList()

        return Either.Right(users)
    }

    override suspend fun findByUid(uid: String): Either<DomainError, UserDto> {
        val user = userStore
            .singleOrNull { it.uid == uid }
            ?: return Either.Left(DomainError.EntityNotFoundError)

        return Either.Right(user)
    }

    override suspend fun findByEmail(email: String): Either<DomainError, UserDto> {
        val user = userStore
            .singleOrNull { it.email == email }
            ?: return Either.Left(DomainError.EntityNotFoundError)

        return Either.Right(user)
    }

    override suspend fun findByEmailOrNull(email: String): Either<DomainError, UserDto?> {
        val user = userStore
            .singleOrNull { it.email == email }

        return Either.Right(user)
    }

    override suspend fun create(data: CreateUserDto): Either<DomainError, EntityUid> {
        val now = LocalDateTime.now()
        val user = UserDto(
            uid = UUID.randomUUID().toString(),
            fullName = data.fullName,
            email = data.email,
            passwordHash = data.passwordHash,
            createdAt = now.toKotlinLocalDateTime(),
            updatedAt = now.toKotlinLocalDateTime()
        )
        userStore.add(user)

        return Either.Right(EntityUid(user.uid))
    }

    override suspend fun update(uid: String, data: UpdateUserDto): Either<DomainError, EntityUid> {
        userStore = userStore
            .map { user ->
                val now = LocalDateTime.now()
                if (user.uid != uid) {
                    return@map user
                }

                user.copy(
                    fullName = data.fullName,
                    email = data.email,
                    passwordHash = data.passwordHash,
                    updatedAt = now.toKotlinLocalDateTime()
                )
            }.toMutableList()

        return Either.Right(EntityUid(uid))
    }

    override suspend fun delete(uid: String): Either<DomainError, EntityUid> {
        userStore
            .removeIf { it.uid == uid }

        return Either.Right(EntityUid(uid))
    }

}