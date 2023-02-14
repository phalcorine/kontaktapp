package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class ContactDetailPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: ContactDetailPageInputHandler,
    eventHandler: ContactDetailPageEventHandler
) : BasicViewModel<
        ContactDetailPageContract.Inputs,
        ContactDetailPageContract.Events,
        ContactDetailPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = ContactDetailPageContract.State(),
            name = "ContactDetailPage",
        )
        .build(),
    eventHandler = eventHandler,
)
