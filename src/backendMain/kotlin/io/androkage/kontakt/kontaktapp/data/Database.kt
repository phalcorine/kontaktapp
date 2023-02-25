package io.androkage.kontakt.kontaktapp.data

import io.androkage.kontakt.kontaktapp.data.entities.ContactEmailTable
import io.androkage.kontakt.kontaktapp.data.entities.ContactPhoneNumberTable
import io.androkage.kontakt.kontaktapp.data.entities.ContactTable
import io.androkage.kontakt.kontaktapp.data.entities.UserTable
import io.androkage.kontakt.kontaktapp.plugins.DatabaseConfig
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(databaseConfig: DatabaseConfig) {
        Database.connect(
            url = databaseConfig.jdbcUrl,
            driver = databaseConfig.driverClassName,
            user = databaseConfig.user,
            password = databaseConfig.password
        )

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.createMissingTablesAndColumns(
                ContactTable,
                ContactEmailTable,
                ContactPhoneNumberTable,
                UserTable
            )
        }
    }

    suspend fun <T>dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}