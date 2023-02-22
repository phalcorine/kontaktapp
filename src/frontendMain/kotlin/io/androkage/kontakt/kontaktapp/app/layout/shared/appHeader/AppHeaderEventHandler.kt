package io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel

class AppHeaderEventHandler(
    private val routerViewModel: AppRouterViewModel
) : EventHandler<
        AppHeaderContract.Inputs,
        AppHeaderContract.Events,
        AppHeaderContract.State> {
    override suspend fun EventHandlerScope<
            AppHeaderContract.Inputs,
            AppHeaderContract.Events,
            AppHeaderContract.State>.handleEvent(
        event: AppHeaderContract.Events
    ) = when (event) {
        is AppHeaderContract.Events.LogOut -> {
            postInput(AppHeaderContract.Inputs.LogOut)
        }

        is AppHeaderContract.Events.NavigateToLogin -> {
            routerViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.Login.directions().build()
                )
            )
            Unit
        }
    }
}
