package io.androkage.kontakt.kontaktapp.plugins

import io.androkage.kontakt.kontaktapp.data.facades.*
import io.androkage.kontakt.kontaktapp.endpoints.*
import io.androkage.kontakt.kontaktapp.util.JwtService
import io.ktor.server.application.*
import io.kvision.remote.kvisionInit
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val DI_NAMED_IN_MEMORY = "in-memory"
val DI_NAMED_DATABASE = "database"

fun Application.configureDI(appConfig: AppConfig) {

    val appModule = module {
        // App Config
        single { appConfig }

        // Facades
        single<ContactFacade> {
            if (appConfig.settings.useInMemoryStorage) ContactFacadeInMemoryImpl else ContactFacadeDatabaseImpl
        }
        single<ContactEmailFacade> {
            if (appConfig.settings.useInMemoryStorage) ContactEmailFacadeInMemoryImpl else ContactEmailFacadeDatabaseImpl
        }
        single<ContactPhoneNumberFacade> {
            if (appConfig.settings.useInMemoryStorage) ContactPhoneNumberFacadeInMemoryImpl else ContactPhoneNumberFacadeDatabaseImpl
        }
        single<UserFacade> {
            if (appConfig.settings.useInMemoryStorage) UserFacadeInMemoryImpl else UserFacadeDatabaseImpl
        }

        // Endpoints
        singleOf(::AuthEndpointService) {
            bind<IAuthEndpointService>()
        }

        factoryOf(::AuthCheckerEndpointService) {
            bind<IAuthCheckerEndpointService>()
        }

        factoryOf(::ContactEndpointService) {
            bind<IContactEndpointService>()
        }

        // Utils
        factoryOf(::JwtService)
    }

    // Kvision
    kvisionInit(appModule)
}