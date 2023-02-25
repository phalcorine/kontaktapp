package io.androkage.kontakt.kontaktapp.app.landing.pages.landingPage

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class LandingPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    eventHandler: LandingPageEventHandler
) : BasicViewModel<
        LandingPageContract.Inputs,
        LandingPageContract.Events,
        LandingPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = LandingPageInputHandler(),
            initialState = LandingPageContract.State(),
            name = "LandingPage",
        )
        .build(),
    eventHandler = eventHandler,
)
