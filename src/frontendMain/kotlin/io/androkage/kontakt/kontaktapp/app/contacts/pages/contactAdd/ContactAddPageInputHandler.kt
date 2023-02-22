package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import io.androkage.kontakt.kontaktapp.endpoints.IContactEndpointService

class ContactAddPageInputHandler(
    private val contactEndpointService: IContactEndpointService
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

            val apiResult = runCatching {
                contactEndpointService.create(input.data)
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactAddPageContract.Events.SuccessMessage("Contact Added Successfully!"))
                postEvent(ContactAddPageContract.Events.NavigateToContactListPage)
            }, {
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactAddPageContract.Events.ErrorMessage(it.message ?: "API Error!"))
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
