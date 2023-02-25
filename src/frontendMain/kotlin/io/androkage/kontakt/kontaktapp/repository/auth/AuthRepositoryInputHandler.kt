package io.androkage.kontakt.kontaktapp.repository.auth

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import io.androkage.kontakt.kontaktapp.dto.AuthLoggedInUserDto
import io.androkage.kontakt.kontaktapp.endpoints.IAuthCheckerEndpointService
import io.androkage.kontakt.kontaktapp.endpoints.IAuthEndpointService
import io.androkage.kontakt.kontaktapp.util.LocalStorageFacade

class AuthRepositoryInputHandler(
    private val eventBus: EventBus,
    private val authCheckerEndpointService: IAuthCheckerEndpointService
) : InputHandler<
        AuthRepositoryContract.Inputs,
        Any,
        AuthRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            AuthRepositoryContract.Inputs,
            Any,
            AuthRepositoryContract.State>.handleInput(
        input: AuthRepositoryContract.Inputs
    ) = when (input) {
        is AuthRepositoryContract.Inputs.Initialize -> {
            // Initialize the Auth Repository

            val previousState = getCurrentState()

            if (!previousState.initialized) {
                val accessToken: String? = LocalStorageFacade.getAccessToken()
                val userData: AuthLoggedInUserDto? = LocalStorageFacade.getUserData()
                val isLoggedIn = accessToken != null && userData != null

                updateState { state ->
                    state.copy(
                        initialized = true,
                        isLoggedIn = isLoggedIn,
                        accessToken = accessToken,
                        userData = userData
                    )
                }

                // Check if current auth credentials are still valid
                postInput(AuthRepositoryContract.Inputs.CheckAuth)

                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe Auth Changes",
                    eventBus
                        .observeInputsFromBus(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }

        is AuthRepositoryContract.Inputs.LogIn -> {
            // Logs in the user
            LocalStorageFacade.saveAccessToken(input.accessToken)
            LocalStorageFacade.saveUserData(input.userData)

            updateState { state ->
                state.copy(
                    isLoggedIn = true,
                    accessToken = input.accessToken,
                    userData = input.userData
                )
            }
        }

        //
        is AuthRepositoryContract.Inputs.LogOut -> {
            LocalStorageFacade.deleteAccessToken()
            LocalStorageFacade.deleteUserData()

            updateState { state ->
                state.copy(
                    isLoggedIn = false,
                    accessToken = null,
                    userData = null
                )
            }
        }

        is AuthRepositoryContract.Inputs.CheckAuth -> {
            val previousState = getCurrentState()

            if (previousState.initialized) {
                val apiResult = authCheckerEndpointService.checkAuth()

                if (apiResult.isFailure) {
                    postInput(AuthRepositoryContract.Inputs.LogOut)
                } else {
                    noOp()
                }
            } else {
                noOp()
            }
        }
    }
}
