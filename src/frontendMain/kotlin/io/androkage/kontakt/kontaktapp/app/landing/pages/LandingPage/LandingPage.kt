package io.androkage.kontakt.kontaktapp.app.landing.pages.LandingPage

import io.androkage.kontakt.kontaktapp.app.layout.mainLayout.mainLayout
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.state.bind
import io.kvision.toolbar.buttonGroup
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Container.landingPage() : KoinComponent = object : KoinComponent {
    private val vm by inject<LandingPageViewModel>()
    init {
        mainLayout("Landing Page") {
            div().bind(vm) { pageState ->
                if (pageState.loading) {
                    h6 { +"Initializing..." }
                }

                h3 { +"Welcome to the Landing Page" }
                p { +"Click on the button below to go to the Contacts Page" }
                buttonGroup {
                    button("Contact List") {
                        onClick {
                            vm.trySend(LandingPageContract.Inputs.NavigateToContactListPage)
                        }
                    }
                }
            }
        }

    }
}