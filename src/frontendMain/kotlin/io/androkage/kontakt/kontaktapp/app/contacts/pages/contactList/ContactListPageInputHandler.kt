package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import io.androkage.kontakt.kontaktapp.endpoints.IContactEndpointService

class ContactListPageInputHandler(
    private val contactEndpointService: IContactEndpointService
) : InputHandler<
        ContactListPageContract.Inputs,
        ContactListPageContract.Events,
        ContactListPageContract.State> {
    override suspend fun InputHandlerScope<
            ContactListPageContract.Inputs,
            ContactListPageContract.Events,
            ContactListPageContract.State>.handleInput(
        input: ContactListPageContract.Inputs
    ) = when (input) {
        is ContactListPageContract.Inputs.RefreshContacts -> {
            updateState { state -> state.copy(loading = true) }

            val apiResult = runCatching {
                contactEndpointService.list()
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({ response ->
                updateState { state -> state.copy(loading = false) }
                postInput(ContactListPageContract.Inputs.LoadedContacts(response.data))
            }, { error ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactListPageContract.Events.ErrorMessage(error.message ?: "API Error!"))
            })
        }

        is ContactListPageContract.Inputs.LoadedContacts -> {
            updateState { state -> state.copy(contacts = input.contacts) }
            postEvent(ContactListPageContract.Events.SuccessMessage("Contacts loaded successfully!"))
        }

        is ContactListPageContract.Inputs.NavigateToContactAddPage -> {
            postEvent(ContactListPageContract.Events.NavigateToContactAddPage)
        }

        is ContactListPageContract.Inputs.SuccessMessage -> {
            postEvent(ContactListPageContract.Events.SuccessMessage(input.message))
        }

        is ContactListPageContract.Inputs.ErrorMessage -> {
            postEvent(ContactListPageContract.Events.ErrorMessage(input.message))
        }

        is ContactListPageContract.Inputs.NavigateToContactDetailPage -> {
            postEvent(ContactListPageContract.Events.NavigateToContactDetailPage(input.contactUid))
        }
    }
}
