package io.androkage.kontakt.kontaktapp.app.auth.pages.register

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class SignupPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: SignupPageInputHandler,
    eventHandler: SignupPageEventHandler
) : BasicViewModel<
        SignupPageContract.Inputs,
        SignupPageContract.Events,
        SignupPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = SignupPageContract.State(),
            name = "RegisterPage",
        )
        .build(),
    eventHandler = eventHandler,
)
