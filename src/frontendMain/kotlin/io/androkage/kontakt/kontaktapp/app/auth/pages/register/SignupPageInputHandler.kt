package io.androkage.kontakt.kontaktapp.app.auth.pages.register

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import io.androkage.kontakt.kontaktapp.dto.AuthRegisterRequestDto
import io.androkage.kontakt.kontaktapp.endpoints.IAuthEndpointService

class SignupPageInputHandler(
    private val authEndpointService: IAuthEndpointService,
) : InputHandler<
        SignupPageContract.Inputs,
        SignupPageContract.Events,
        SignupPageContract.State> {
    override suspend fun InputHandlerScope<
            SignupPageContract.Inputs,
            SignupPageContract.Events,
            SignupPageContract.State>.handleInput(
        input: SignupPageContract.Inputs
    ) = when (input) {
        is SignupPageContract.Inputs.AttemptSignup -> {
            updateState { it.copy(formSubmitting = true) }

            val apiResult = runCatching {
                authEndpointService.signup(
                    AuthRegisterRequestDto(
                        fullName = input.fullName,
                        email = input.email,
                        password = input.password
                    )
                )
            }.getOrElse {
                Result.failure(it)
            }

            apiResult.fold({ response ->
                // Redirect to Login
                updateState { it.copy(formSubmitting = false) }
                postEvent(SignupPageContract.Events.ShowSuccess(response.message))
                postEvent(SignupPageContract.Events.NavigateToLogin)
            }, { error ->
                updateState { it.copy(formSubmitting = false) }
                postEvent(SignupPageContract.Events.ShowError(error.message ?: "API Error!"))
            })
        }

        is SignupPageContract.Inputs.ShowError -> {
            postEvent(SignupPageContract.Events.ShowError(input.message))
        }

        is SignupPageContract.Inputs.ShowSuccess -> {
            postEvent(SignupPageContract.Events.ShowSuccess(input.message))
        }
    }
}
