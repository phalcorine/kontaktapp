package io.androkage.kontakt.kontaktapp

import io.androkage.kontakt.kontaktapp.data.DatabaseFactory
import io.androkage.kontakt.kontaktapp.plugins.configureDI
import io.androkage.kontakt.kontaktapp.plugins.configureRouting
import io.androkage.kontakt.kontaktapp.plugins.loadConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val appConfig = loadConfig()
    configureRouting()
    configureDI(appConfig)

    // Load database if not using in-memory storage
    if (!appConfig.settings.useInMemoryStorage) {
        DatabaseFactory.init(appConfig.database)
    }

}
