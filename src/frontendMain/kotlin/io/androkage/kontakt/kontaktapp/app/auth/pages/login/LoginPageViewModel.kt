package io.androkage.kontakt.kontaktapp.app.auth.pages.login

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class LoginPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: LoginPageInputHandler,
    eventHandler: LoginPageEventHandler
) : BasicViewModel<
        LoginPageContract.Inputs,
        LoginPageContract.Events,
        LoginPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = LoginPageContract.State(),
            name = "LoginPage",
        )
        .build(),
    eventHandler = eventHandler,
)
