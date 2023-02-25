package io.androkage.kontakt.kontaktapp.app.landing.pages.landingPage

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel

class LandingPageEventHandler(
    private val routerViewModel: AppRouterViewModel
) : EventHandler<
        LandingPageContract.Inputs,
        LandingPageContract.Events,
        LandingPageContract.State> {
    override suspend fun EventHandlerScope<
            LandingPageContract.Inputs,
            LandingPageContract.Events,
            LandingPageContract.State>.handleEvent(
        event: LandingPageContract.Events
    ) = when (event) {
        is LandingPageContract.Events.NavigateToContactListPage -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.ContactList.directions().build()
                )
            )
            Unit
        }
    }
}
