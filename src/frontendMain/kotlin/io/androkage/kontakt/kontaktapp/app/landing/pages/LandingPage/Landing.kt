package io.androkage.kontakt.kontaktapp.app.landing.pages.LandingPage

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val landingPageModule = module {
    singleOf(::LandingPageEventHandler)
    singleOf(::LandingPageViewModel)
}