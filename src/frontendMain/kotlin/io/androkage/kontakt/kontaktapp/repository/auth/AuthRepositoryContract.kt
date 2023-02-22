package io.androkage.kontakt.kontaktapp.repository.auth

import io.androkage.kontakt.kontaktapp.dto.AuthLoggedInUserDto

object AuthRepositoryContract {
    data class State(
        val initialized: Boolean = false,
        val isLoggedIn: Boolean = true,
        val accessToken: String? = null,
        val userData: AuthLoggedInUserDto? = null
    )

    sealed class Inputs {
        object Initialize : Inputs()
        data class LogIn(val accessToken: String, val userData: AuthLoggedInUserDto) : Inputs()
        object LogOut : Inputs()
        object CheckAuth : Inputs()
    }
}
