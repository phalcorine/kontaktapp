package io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class AppHeaderViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: AppHeaderInputHandler,
    eventHandler: AppHeaderEventHandler
) : BasicViewModel<
        AppHeaderContract.Inputs,
        AppHeaderContract.Events,
        AppHeaderContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = AppHeaderContract.State(),
            name = "AppHeader",
        )
        .build(),
    eventHandler = eventHandler,
)
