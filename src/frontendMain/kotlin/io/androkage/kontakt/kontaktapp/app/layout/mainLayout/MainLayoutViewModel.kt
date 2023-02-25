package io.androkage.kontakt.kontaktapp.app.layout.mainLayout

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class MainLayoutViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: MainLayoutInputHandler,
    eventHandler: MainLayoutEventHandler
) : BasicViewModel<
        MainLayoutContract.Inputs,
        MainLayoutContract.Events,
        MainLayoutContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = MainLayoutContract.State(),
            name = "MainLayout",
        )
        .build(),
    eventHandler = eventHandler,
)
