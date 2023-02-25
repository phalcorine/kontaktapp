package io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader

object AppHeaderContract {
    data class State(
        val isLoggedIn: Boolean = false,
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object LogOut : Inputs()
        data class LoggedInStateHasChanged(val isLoggedIn: Boolean) : Inputs()
    }

    sealed class Events {
        object LogOut : Events()
        object NavigateToLogin : Events()
    }
}
