package io.androkage.kontakt.kontaktapp.app.auth.pages.register

object SignupPageContract {
    data class State(
        val formSubmitting: Boolean = false,
    ) {
        val isSignupButtonDisabled get() = formSubmitting
    }

    sealed class Inputs {
        data class AttemptSignup(
            val fullName: String,
            val email: String,
            val password: String
        ) : Inputs()
        data class ShowError(val message: String) : Inputs()
        data class ShowSuccess(val message: String) : Inputs()
    }

    sealed class Events {
        object NavigateToLogin : Events()
        data class ShowError(val message: String) : Events()
        data class ShowSuccess(val message: String) : Events()
    }
}
