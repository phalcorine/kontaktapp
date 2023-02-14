package io.androkage.kontakt.kontaktapp.app.landing.pages.LandingPage

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.delay

class LandingPageInputHandler : InputHandler<
        LandingPageContract.Inputs,
        LandingPageContract.Events,
        LandingPageContract.State> {
    override suspend fun InputHandlerScope<
            LandingPageContract.Inputs,
            LandingPageContract.Events,
            LandingPageContract.State>.handleInput(
        input: LandingPageContract.Inputs
    ) = when (input) {
        is LandingPageContract.Inputs.Initialize -> {
            updateState { it.copy(loading = true) }
            delay(1000)
            updateState { it.copy(loading = false) }
        }

        is LandingPageContract.Inputs.NavigateToContactListPage -> {
            postEvent(LandingPageContract.Events.NavigateToContactListPage)
        }
    }
}
