package io.androkage.kontakt.kontaktapp.app.auth.pages

import io.androkage.kontakt.kontaktapp.app.auth.pages.login.loginPageModule
import io.androkage.kontakt.kontaktapp.app.auth.pages.register.signupPageModule
import org.koin.dsl.module

val authModule = module {
    includes(loginPageModule, signupPageModule)
}