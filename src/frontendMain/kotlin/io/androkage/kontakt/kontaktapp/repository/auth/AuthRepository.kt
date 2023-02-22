package io.androkage.kontakt.kontaktapp.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import io.androkage.kontakt.kontaktapp.dto.AuthLoggedInUserDto
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun initialize()
    fun login(accessToken: String, userData: AuthLoggedInUserDto)
    fun logOut()
    fun isLoggedIn(): Flow<Boolean>
}
