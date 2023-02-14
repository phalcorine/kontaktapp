package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import io.androkage.kontakt.kontaktapp.dto.CreateContactDto

object ContactAddPageContract {
    data class State(
        val loading: Boolean = false,
    )

    sealed class Inputs {
        data class CreateContact(val data: CreateContactDto) : Inputs()
        data class ErrorMessage(val message: String) : Inputs()
        object NavigateToContactListPage : Inputs()
    }

    sealed class Events {
        data class SuccessMessage(val message: String) : Events()
        data class ErrorMessage(val message: String) : Events()
        object NavigateToContactListPage : Events()
    }
}
