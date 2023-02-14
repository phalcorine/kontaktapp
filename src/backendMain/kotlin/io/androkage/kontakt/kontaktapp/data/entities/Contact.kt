package io.androkage.kontakt.kontaktapp.data.entities

import io.androkage.kontakt.kontaktapp.dto.ContactDto
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ContactTable : IntIdTable("contacts") {
    val uid = varchar("uid", 100).uniqueIndex()
    val name = varchar("name", 100)
    val note = varchar("note", 200).nullable()
    val created_at = datetime("created_at")
    val updated_at = datetime("updated_at")
}

fun ResultRow.toContactDto() = ContactDto(
    uid = this[ContactTable.uid],
    name = this[ContactTable.name],
    note = this[ContactTable.note],
    createdAt = this[ContactTable.created_at],
    updatedAt = this[ContactTable.updated_at],
)