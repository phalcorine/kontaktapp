package io.androkage.kontakt.kontaktapp.app

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.core.JsConsoleBallastLogger
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.EventBusImpl
import io.androkage.kontakt.kontaktapp.AppScope
import io.androkage.kontakt.kontaktapp.api.endpointModule
import io.androkage.kontakt.kontaktapp.app.auth.pages.authModule
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactModule
import io.androkage.kontakt.kontaktapp.app.landing.pages.landingPage.landingPageModule
import io.androkage.kontakt.kontaktapp.app.layout.appLayoutModule
import io.androkage.kontakt.kontaktapp.app.layout.shared.sharedComponentsModule
import io.androkage.kontakt.kontaktapp.repository.repositoryModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initializeKoin() {
    val appModule = module {
        // Global Coroutine Scope (JavaScript is single-threaded, so...)
        single { AppScope }

        // Ballast Configuration
        factory {
            BallastViewModelConfiguration.Builder().apply {
                this += LoggingInterceptor()
                logger = { JsConsoleBallastLogger(it) }
            }
        }

        // Ballast EventBus for Repositories
        single<EventBus> { EventBusImpl() }

        // Import all modules...
        includes(
            endpointModule,
            routerModule,
            repositoryModule,
            sharedComponentsModule,
            appLayoutModule,
            landingPageModule,
            authModule,
            contactModule
        )
    }

    startKoin {
        modules(appModule)
    }
}