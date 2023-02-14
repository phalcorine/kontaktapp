package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class ContactListPageViewModel(
    coroutineScope: CoroutineScope,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: ContactListPageInputHandler,
    eventHandler: ContactListPageEventHandler
) : BasicViewModel<
        ContactListPageContract.Inputs,
        ContactListPageContract.Events,
        ContactListPageContract.State>(
    coroutineScope = coroutineScope,
    config = configBuilder
        .withViewModel(
            inputHandler = inputHandler,
            initialState = ContactListPageContract.State(),
            name = "ContactListPage",
        )
        .build(),
    eventHandler = eventHandler,
)
