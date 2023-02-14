package io.androkage.kontakt.kontaktapp.plugins

import io.ktor.server.application.*

data class AppConfig(
    val database: DatabaseConfig,
    val settings: SettingsConfig,
)

data class DatabaseConfig(
    val driverClassName: String,
    val jdbcUrl: String,
    val user: String,
    val password: String,
)

data class SettingsConfig(
    val useInMemoryStorage: Boolean
)

fun Application.loadConfig(): AppConfig {
    val driverClassName = environment.config.property("db.driverClassName").getString()
    val jdbcUrl = environment.config.property("db.jdbcUrl").getString()
    val user = environment.config.property("db.user").getString()
    val password = environment.config.property("db.password").getString()
    val useInMemoryStorage = environment.config.property("settings.useInMemoryStorage").getString()
        .toBooleanStrictOrNull() ?: true

    return AppConfig(
        database = DatabaseConfig(
            driverClassName, jdbcUrl, user, password
        ),
        settings = SettingsConfig(
            useInMemoryStorage = useInMemoryStorage
        )
    )
}