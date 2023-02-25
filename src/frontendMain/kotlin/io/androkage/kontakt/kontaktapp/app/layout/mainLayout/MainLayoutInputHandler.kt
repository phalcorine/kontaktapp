package io.androkage.kontakt.kontaktapp.app.layout.mainLayout

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import io.androkage.kontakt.kontaktapp.repository.auth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

class MainLayoutInputHandler(
    private val authRepository: AuthRepository
) : InputHandler<
        MainLayoutContract.Inputs,
        MainLayoutContract.Events,
        MainLayoutContract.State> {
    override suspend fun InputHandlerScope<
            MainLayoutContract.Inputs,
            MainLayoutContract.Events,
            MainLayoutContract.State>.handleInput(
        input: MainLayoutContract.Inputs
    ) = when (input) {
        is MainLayoutContract.Inputs.Initialize -> {
            // Wait for initialization here, maybe...
            /*updateState { it.copy(loading = true) }
            delay(1000)
            updateState { it.copy(loading = false) }*/
            observeFlows(
                key = "Observe Logged In State",
                authRepository
                    .isLoggedIn()
                    .map {
                        MainLayoutContract.Inputs.LoggedInStateHasChanged(it)
                    }
            )
        }

        is MainLayoutContract.Inputs.LoggedInStateHasChanged -> {
            sideJob("LoggedInStateHasChanged") {
                if (!input.isLoggedIn) {
                    postEvent(MainLayoutContract.Events.NavigateToLogin)
                }
            }
        }
    }
}
