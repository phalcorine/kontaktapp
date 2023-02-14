package io.androkage.kontakt.kontaktapp.app

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.core.JsConsoleBallastLogger
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import io.androkage.kontakt.kontaktapp.AppScope
import io.androkage.kontakt.kontaktapp.api.endpointModule
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactModule
import io.androkage.kontakt.kontaktapp.app.landing.pages.LandingPage.landingPageModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initializeKoin() {
    val appModule = module {
        single { AppScope }

        single {
            BallastViewModelConfiguration.Builder().apply {
                this += LoggingInterceptor()
                logger = { JsConsoleBallastLogger(it) }
            }
        }

        includes(endpointModule, routerModule, landingPageModule, contactModule)
    }

    startKoin {
        modules(appModule)
    }
}