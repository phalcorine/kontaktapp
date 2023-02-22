package io.androkage.kontakt.kontaktapp.app.layout

import io.androkage.kontakt.kontaktapp.app.layout.mainLayout.mainLayoutModule
import org.koin.dsl.module

val appLayoutModule = module {
    includes(mainLayoutModule)
}