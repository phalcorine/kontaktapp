package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class ContactAddPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: ContactAddPageInputHandler,
    eventHandler: ContactAddPageEventHandler
) : BasicViewModel<
        ContactAddPageContract.Inputs,
        ContactAddPageContract.Events,
        ContactAddPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = ContactAddPageContract.State(),
            name = "ContactAddPage",
        )
        .build(),
    eventHandler = eventHandler,
)
