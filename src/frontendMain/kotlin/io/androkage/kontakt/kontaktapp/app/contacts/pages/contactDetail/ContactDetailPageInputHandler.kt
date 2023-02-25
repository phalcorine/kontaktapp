package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import io.androkage.kontakt.kontaktapp.endpoints.IContactEndpointService

class ContactDetailPageInputHandler(
    private val contactEndpointService: IContactEndpointService
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

            val apiResult = runCatching {
                contactEndpointService.findByUid(input.contactUid)
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({ response ->
                updateState { state -> state.copy(loading = false, contact = response.data) }
                postEvent(ContactDetailPageContract.Events.SuccessMessage("Contact loaded successfully!"))
            }, { error ->
                updateState { state -> state.copy(loading = true) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message ?: "API Error!"))
            })
        }

        is ContactDetailPageContract.Inputs.UpdateContact -> {
            updateState { state -> state.copy(loading = true) }

            val apiResult = runCatching {
                contactEndpointService.update(input.contactUid, input.data)
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({ _ ->
                updateState { state -> state.copy(loading = false) }
                postInput(ContactDetailPageContract.Inputs.DisableEditMode)
                postInput(ContactDetailPageContract.Inputs.Initialize(input.contactUid))
            }) { error ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message ?: "API Error!"))
            }
        }

        is ContactDetailPageContract.Inputs.DeleteContact -> {
            updateState { state -> state.copy(loading = true) }

            val apiResult = runCatching {
                contactEndpointService.delete(input.contactUid)
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({ _ ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.NavigateToContactDetailPage)
            }) { error ->
                updateState { state -> state.copy(loading = false) }
                postEvent(ContactDetailPageContract.Events.ErrorMessage(error.message ?: "API Error!"))
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
