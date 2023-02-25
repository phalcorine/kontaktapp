package io.androkage.kontakt.kontaktapp.plugins

import io.ktor.server.application.*

data class AppConfig(
    val database: DatabaseConfig,
    val jwt: JwtConfig,
    val settings: SettingsConfig,
)

data class DatabaseConfig(
    val driverClassName: String,
    val jdbcUrl: String,
    val user: String,
    val password: String,
)

data class SettingsConfig(
    val useInMemoryStorage: Boolean,
)

data class JwtConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String,
)

fun Application.loadConfig(): AppConfig {
    // Database
    val driverClassName = environment.config.property("db.driverClassName").getString()
    val jdbcUrl = environment.config.property("db.jdbcUrl").getString()
    val user = environment.config.property("db.user").getString()
    val password = environment.config.property("db.password").getString()

    val jwtDomain = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    // Settings
    val useInMemoryStorage = environment.config.property("settings.useInMemoryStorage").getString()
        .toBooleanStrictOrNull() ?: true

    return AppConfig(
        database = DatabaseConfig(
            driverClassName, jdbcUrl, user, password
        ),
        jwt = JwtConfig(
            domain = jwtDomain,
            audience = jwtAudience,
            realm = jwtRealm,
            secret = jwtSecret,
        ),
        settings = SettingsConfig(
            useInMemoryStorage = useInMemoryStorage
        )
    )
}