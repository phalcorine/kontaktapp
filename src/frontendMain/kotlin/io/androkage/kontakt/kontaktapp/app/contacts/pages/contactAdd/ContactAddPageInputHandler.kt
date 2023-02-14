package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService

class ContactAddPageInputHandler(
    private val contactEndpointService: ContactEndpointService
) : InputHandler<
        ContactAddPageContract.Inputs,
        ContactAddPageContract.Events,
        ContactAddPageContract.State> {
    override suspend fun InputHandlerScope<
            ContactAddPageContract.Inputs,
            ContactAddPageContract.Events,
            ContactAddPageContract.State>.handleInput(
        input: ContactAddPageContract.Inputs
    ) = when (input) {
        is ContactAddPageContract.Inputs.CreateContact -> {
            updateState { state -> state.copy(loading = true) }

            val eitherResult = contactEndpointService.create(input.data)
            eitherResult.fold({
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactAddPageContract.Events.ErrorMessage(it.message))
            }, {
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactAddPageContract.Events.SuccessMessage("Contact Added Successfully!"))
                postEvent(ContactAddPageContract.Events.NavigateToContactListPage)
            })
        }

        is ContactAddPageContract.Inputs.ErrorMessage -> {
            postEvent(ContactAddPageContract.Events.ErrorMessage(input.message))
        }

        is ContactAddPageContract.Inputs.NavigateToContactListPage -> {
            postEvent(ContactAddPageContract.Events.NavigateToContactListPage)
        }
    }
}
