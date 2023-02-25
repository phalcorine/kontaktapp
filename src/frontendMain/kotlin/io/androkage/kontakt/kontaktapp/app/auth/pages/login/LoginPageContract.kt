package io.androkage.kontakt.kontaktapp.app.auth.pages.login

object LoginPageContract {
    data class State(
        val formSubmitting: Boolean = false,
    ) {
        val isLoginButtonDisabled get() = formSubmitting
    }

    sealed class Inputs {
        data class AttemptLogin(val email: String, val password: String) : Inputs()
        data class ShowError(val message: String) : Inputs()
        data class ShowSuccess(val message: String) : Inputs()
    }

    sealed class Events {
        object NavigateToDashboard : Events()
        data class ShowError(val message: String) : Events()
        data class ShowSuccess(val message: String) : Events()
    }
}
