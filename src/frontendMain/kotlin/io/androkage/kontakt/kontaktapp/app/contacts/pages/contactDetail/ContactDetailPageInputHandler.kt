package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService

class ContactDetailPageInputHandler(
    private val contactEndpointService: ContactEndpointService
) : InputHandler<
        ContactDetailPageContract.Inputs,
        ContactDetailPageContract.Events,
        ContactDetailPageContract.State> {
    override suspend fun InputHandlerScope<
            ContactDetailPageContract.Inputs,
            ContactDetailPageContract.Events,
            ContactDetailPageContract.State>.handleInput(
        input: ContactDetailPageContract.Inputs
    ) = when (input) {
        is ContactDetailPageContract.Inputs.Initialize -> {
            updateState { it.copy(loading = true) }

            val eitherResult = contactEndpointService.findByUid(input.contactUid)
            eitherResult.fold({ error ->
                updateState { state -> state.copy(loading = true) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message))
            }, { apiResponse ->
                updateState { state -> state.copy(loading = false, contact = apiResponse.data) }
                postEvent(ContactDetailPageContract.Events.SuccessMessage("Contact loaded successfully!"))
            })
        }

        is ContactDetailPageContract.Inputs.UpdateContact -> {
            updateState { state -> state.copy(loading = true) }
            val eitherResult = contactEndpointService.update(input.contactUid, input.data)
            eitherResult.fold({ error ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message))
            }) { _ ->
                updateState { state -> state.copy(loading = false) }
                postInput(ContactDetailPageContract.Inputs.Initialize(input.contactUid))
                postInput(ContactDetailPageContract.Inputs.DisableEditMode)
            }
        }

        is ContactDetailPageContract.Inputs.DeleteContact -> {
            updateState { state -> state.copy(loading = true) }

            val eitherResult = contactEndpointService.delete(input.contactUid)
            eitherResult.fold({ error ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message))
            }) { _ ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.NavigateToContactDetailPage)
            }
        }

        is ContactDetailPageContract.Inputs.SetContact -> {
            updateState { state -> state.copy(contact = input.contact) }
        }

        is ContactDetailPageContract.Inputs.SuccessMessage -> {
            postEvent(ContactDetailPageContract.Events.SuccessMessage(input.message))
        }

        is ContactDetailPageContract.Inputs.ErrorMessage -> {
            postEvent(ContactDetailPageContract.Events.ErrorMessage(input.message))
        }

        is ContactDetailPageContract.Inputs.EnableEditMode -> {
            updateState { state -> state.copy(isEditing = true) }
        }

        is ContactDetailPageContract.Inputs.DisableEditMode -> {
            updateState { state -> state.copy(isEditing = false) }
        }
    }
}
