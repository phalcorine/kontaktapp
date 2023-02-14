package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.kvision.toast.Toast

class ContactAddPageEventHandler(
    private val routerViewModel: AppRouterViewModel
) : EventHandler<
        ContactAddPageContract.Inputs,
        ContactAddPageContract.Events,
        ContactAddPageContract.State> {
    override suspend fun EventHandlerScope<
            ContactAddPageContract.Inputs,
            ContactAddPageContract.Events,
            ContactAddPageContract.State>.handleEvent(
        event: ContactAddPageContract.Events
    ) = when (event) {
        is ContactAddPageContract.Events.ErrorMessage -> {
            Toast.danger(event.message)
        }

        is ContactAddPageContract.Events.SuccessMessage -> {
            Toast.success(event.message)
        }

        is ContactAddPageContract.Events.NavigateToContactListPage -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.ContactList
                        .directions()
                        .build()
                )
            )
            Unit
        }
    }
}
