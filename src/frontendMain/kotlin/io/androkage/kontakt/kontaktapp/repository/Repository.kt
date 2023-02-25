package io.androkage.kontakt.kontaktapp.repository

import io.androkage.kontakt.kontaktapp.repository.auth.authRepositoryModule
import org.koin.dsl.module

val repositoryModule = module {
    includes(authRepositoryModule)
}