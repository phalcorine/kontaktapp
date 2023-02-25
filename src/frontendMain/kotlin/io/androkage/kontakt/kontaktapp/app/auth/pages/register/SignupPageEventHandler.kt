package io.androkage.kontakt.kontaktapp.app.auth.pages.register

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.kvision.toast.Toast

class SignupPageEventHandler(
    private val appRouterViewModel: AppRouterViewModel
) : EventHandler<
        SignupPageContract.Inputs,
        SignupPageContract.Events,
        SignupPageContract.State> {
    override suspend fun EventHandlerScope<
            SignupPageContract.Inputs,
            SignupPageContract.Events,
            SignupPageContract.State>.handleEvent(
        event: SignupPageContract.Events
    ) = when (event) {
        is SignupPageContract.Events.NavigateToLogin -> {
            appRouterViewModel
                .trySend(
                    RouterContract.Inputs.GoToDestination(
                        AppRouter.Login.directions().build()
                    )
                )
            Unit
        }

        is SignupPageContract.Events.ShowError -> {
            Toast.danger(event.message)
        }

        is SignupPageContract.Events.ShowSuccess -> {
            Toast.success(event.message)
        }
    }
}
