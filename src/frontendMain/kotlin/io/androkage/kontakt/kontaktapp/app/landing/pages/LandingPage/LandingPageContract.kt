package io.androkage.kontakt.kontaktapp.app.landing.pages.LandingPage

object LandingPageContract {
    data class State(
        val loading: Boolean = false,
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object NavigateToContactListPage : Inputs()
    }

    sealed class Events {
        object NavigateToContactListPage : Events()
    }
}
