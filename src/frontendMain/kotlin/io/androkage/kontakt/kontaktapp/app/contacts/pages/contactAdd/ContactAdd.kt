package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val contactAddPageModule = module {
    singleOf(::ContactAddPageInputHandler)
    singleOf(::ContactAddPageEventHandler)
    singleOf(::ContactAddPageViewModel)
}