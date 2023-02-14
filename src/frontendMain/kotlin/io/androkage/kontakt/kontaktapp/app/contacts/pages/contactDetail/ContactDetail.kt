package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val contactDetailPageModule = module {
    singleOf(::ContactDetailPageInputHandler)
    singleOf(::ContactDetailPageEventHandler)
    singleOf(::ContactDetailPageViewModel)
}