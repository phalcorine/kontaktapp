package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val contactListPageModule = module {
    singleOf(::ContactListPageInputHandler)
    singleOf(::ContactListPageEventHandler)
    singleOf(::ContactListPageViewModel)
}