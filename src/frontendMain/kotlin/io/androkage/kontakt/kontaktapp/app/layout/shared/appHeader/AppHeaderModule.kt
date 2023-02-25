package io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appHeaderModule = module {
    singleOf(::AppHeaderEventHandler)
    singleOf(::AppHeaderInputHandler)
    singleOf(::AppHeaderViewModel)
}