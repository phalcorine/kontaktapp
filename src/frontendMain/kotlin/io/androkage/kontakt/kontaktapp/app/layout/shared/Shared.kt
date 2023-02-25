package io.androkage.kontakt.kontaktapp.app.layout.shared

import io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader.appHeaderModule
import org.koin.dsl.module

val sharedComponentsModule = module {
    includes(appHeaderModule)
}