package io.androkage.kontakt.kontaktapp.repository.auth

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.withRepository
import io.androkage.kontakt.kontaktapp.dto.AuthLoggedInUserDto
import io.androkage.kontakt.kontaktapp.endpoints.AuthCheckerEndpointService
import io.androkage.kontakt.kontaktapp.endpoints.IAuthCheckerEndpointService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder,
    authCheckerEndpointService: IAuthCheckerEndpointService
) : BallastRepository<
        AuthRepositoryContract.Inputs,
        AuthRepositoryContract.State>(coroutineScope = coroutineScope, eventBus = eventBus, config = configBuilder
    .apply {
        inputHandler = AuthRepositoryInputHandler(eventBus, authCheckerEndpointService)
        initialState = AuthRepositoryContract.State()
        name = "Auth Repository"
    }.withRepository().build()
), AuthRepository {
    override fun initialize() {
        trySend(AuthRepositoryContract.Inputs.Initialize)
    }
    override fun login(accessToken: String, userData: AuthLoggedInUserDto) {
        trySend(AuthRepositoryContract.Inputs.LogIn(accessToken, userData))
    }

    override fun logOut() {
        trySend(AuthRepositoryContract.Inputs.LogOut)
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return observeStates()
            .map { it.isLoggedIn }
    }
}
