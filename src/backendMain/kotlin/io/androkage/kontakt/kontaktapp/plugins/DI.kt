package io.androkage.kontakt.kontaktapp.plugins

import io.androkage.kontakt.kontaktapp.data.facades.*
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointServiceImpl
import io.ktor.server.application.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val DI_NAMED_IN_MEMORY = "in-memory"
val DI_NAMED_DATABASE = "database"

fun Application.configureDI(appConfig: AppConfig) {
    install(Koin) {
        slf4jLogger()

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

            // Endpoints
            singleOf(::ContactEndpointServiceImpl) {
                bind<ContactEndpointService>()
            }
        }

        modules(appModule)
    }

//    // Kvision
//    kvisionInit(module)
}