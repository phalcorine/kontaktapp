package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.kvision.toast.Toast

class ContactDetailPageEventHandler(
    private val routerViewModel: AppRouterViewModel
) : EventHandler<
        ContactDetailPageContract.Inputs,
        ContactDetailPageContract.Events,
        ContactDetailPageContract.State> {
    override suspend fun EventHandlerScope<
            ContactDetailPageContract.Inputs,
            ContactDetailPageContract.Events,
            ContactDetailPageContract.State>.handleEvent(
        event: ContactDetailPageContract.Events
    ) = when (event) {
        is ContactDetailPageContract.Events.ErrorMessage -> {
            Toast.danger(event.message)
        }

        is ContactDetailPageContract.Events.SuccessMessage -> {
            Toast.success(event.message)
        }

        is ContactDetailPageContract.Events.NavigateToContactDetailPage -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.ContactList.directions().build()
                )
            )
            Unit
        }
    }
}
