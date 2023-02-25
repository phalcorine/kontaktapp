package io.androkage.kontakt.kontaktapp.app.layout.mainLayout

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel

class MainLayoutEventHandler(
    private val appRouterViewModel: AppRouterViewModel
) : EventHandler<
        MainLayoutContract.Inputs,
        MainLayoutContract.Events,
        MainLayoutContract.State> {
    override suspend fun EventHandlerScope<
            MainLayoutContract.Inputs,
            MainLayoutContract.Events,
            MainLayoutContract.State>.handleEvent(
        event: MainLayoutContract.Events
    ) = when (event) {
        is MainLayoutContract.Events.NavigateToLogin -> {
            appRouterViewModel.trySend(
                RouterContract.Inputs.GoToDestination(
                    AppRouter.Login.directions().build()
                )
            )
            Unit
        }
    }
}
