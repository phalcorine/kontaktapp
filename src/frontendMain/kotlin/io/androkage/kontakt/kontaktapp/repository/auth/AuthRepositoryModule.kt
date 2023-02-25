package io.androkage.kontakt.kontaktapp.repository.auth

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authRepositoryModule = module {
    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }
}