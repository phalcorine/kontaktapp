package io.androkage.kontakt.kontaktapp.data.entities

import io.androkage.kontakt.kontaktapp.dto.ContactDto
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ContactTable : IntIdTable("contacts") {
    val uid = varchar("uid", 100).uniqueIndex()
    val name = varchar("name", 100)
    val note = varchar("note", 200).nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    // relations
    val userUid = reference("user_uid", UserTable.uid)
}

fun ResultRow.toContactDto() = ContactDto(
    uid = this[ContactTable.uid],
    name = this[ContactTable.name],
    note = this[ContactTable.note],
    createdAt = this[ContactTable.createdAt],
    updatedAt = this[ContactTable.updatedAt],
    userUid = this[ContactTable.userUid],
)