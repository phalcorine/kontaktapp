package io.androkage.kontakt.kontaktapp.app.contacts.pages

import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd.contactAddPageModule
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail.contactDetailPageModule
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList.contactListPageModule
import org.koin.dsl.module

val contactModule = module {
    includes(contactListPageModule, contactAddPageModule, contactDetailPageModule)
}