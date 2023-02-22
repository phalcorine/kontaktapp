package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val contactListPageModule = module {
    factoryOf(::ContactListPageInputHandler)
    factoryOf(::ContactListPageEventHandler)
    factoryOf(::ContactListPageViewModel)
}