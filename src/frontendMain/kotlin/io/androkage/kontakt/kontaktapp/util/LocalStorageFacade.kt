package io.androkage.kontakt.kontaktapp.util

import io.androkage.kontakt.kontaktapp.dto.AuthLoggedInUserDto
import kotlinx.browser.localStorage

object LocalStorageFacade {
    private const val KEY_ACCESS_TOKEN = "app_access_token"
    private const val KEY_USER_DATA = "app_user_data"
    fun saveAccessToken(accessToken: String) {
        localStorage.setItem(KEY_ACCESS_TOKEN, accessToken)
    }

    fun getAccessToken(): String? {
        return localStorage.getItem(KEY_ACCESS_TOKEN)
    }

    fun deleteAccessToken() {
        localStorage.removeItem(KEY_ACCESS_TOKEN)
    }

    fun saveUserData(userData: AuthLoggedInUserDto) {
        localStorage.setItem(KEY_USER_DATA, JSON.stringify(userData))
    }

    fun getUserData(): AuthLoggedInUserDto? {
        val authData = localStorage.getItem(KEY_USER_DATA);
        return authData?.let {
            JSON.parse<AuthLoggedInUserDto>(it)
        }
    }

    fun deleteUserData() {
        localStorage.removeItem(KEY_USER_DATA)
    }
}