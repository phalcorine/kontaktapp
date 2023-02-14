package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.pathParameter
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.kvision.toast.Toast

class ContactListPageEventHandler(
    private val routerViewModel: AppRouterViewModel
) : EventHandler<
        ContactListPageContract.Inputs,
        ContactListPageContract.Events,
        ContactListPageContract.State> {
    override suspend fun EventHandlerScope<
            ContactListPageContract.Inputs,
            ContactListPageContract.Events,
            ContactListPageContract.State>.handleEvent(
        event: ContactListPageContract.Events
    ) = when (event) {
        is ContactListPageContract.Events.ErrorMessage -> {
            Toast.danger(event.message)
        }

        is ContactListPageContract.Events.SuccessMessage -> {
            Toast.success(event.message)
        }

        is ContactListPageContract.Events.NavigateToContactAddPage -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.ContactAdd
                        .directions()
                        .build()
                )
            )
            Unit
        }

        is ContactListPageContract.Events.NavigateToContactDetailPage -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.ContactDetail
                        .directions()
                        .pathParameter("uid", event.contactUid)
                        .build()
                )
            )
            Unit
        }
    }
}
