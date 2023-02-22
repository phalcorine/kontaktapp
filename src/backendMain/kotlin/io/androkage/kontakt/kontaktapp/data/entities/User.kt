package io.androkage.kontakt.kontaktapp.data.entities

import io.androkage.kontakt.kontaktapp.dto.UserDto
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : IntIdTable("users") {
    val uid = varchar("uid", 100).uniqueIndex()
    val fullName = varchar("full_name", 100)
    val email = varchar("email", 180)
    val passwordHash = varchar("password_hash", 4096)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

fun ResultRow.toUserDto() = UserDto(
    uid = this[UserTable.uid],
    fullName = this[UserTable.fullName],
    email = this[UserTable.email],
    passwordHash = this[UserTable.passwordHash],
    createdAt = this[UserTable.createdAt],
    updatedAt = this[UserTable.updatedAt]
)