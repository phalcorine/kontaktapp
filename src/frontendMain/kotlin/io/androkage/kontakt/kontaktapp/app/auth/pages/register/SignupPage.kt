package io.androkage.kontakt.kontaktapp.app.auth.pages.register

import io.androkage.kontakt.kontaktapp.app.layout.authLayout.authLayout
import io.kvision.core.Container
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.*
import io.kvision.state.bind
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Serializable
data class SignupFormDto(
    val fullName: String = "",
    val email: String = "",
    val password: String = ""
)

fun Container.signupPage() : KoinComponent = object : KoinComponent {
    private val signupPageViewModel by inject<SignupPageViewModel>()
    init {
        authLayout {
            div {
                div(className = "text-center") {
                    h1(className = "h3 fw-normal") {
                        +"Signup"
                    }
                }
                val signupForm = formPanel<SignupFormDto> {
                    add(
                        SignupFormDto::fullName,
                        Text(label = "Full Name", type = InputType.TEXT) {
                            placeholder = "Full Name..."
                        },
                        required = true
                    )
                    add(
                        SignupFormDto::email,
                        Text(label = "Email", type = InputType.EMAIL) {
                            placeholder = "Account Email..."
                        },
                        required = true
                    )
                    add(
                        SignupFormDto::password,
                        Text(label = "Password", type = InputType.PASSWORD) {
                            placeholder = "Account Password..."
                        },
                        required = true
                    )
                }
                div(className = "justify-content-between align-items-center").bind(signupPageViewModel) { uiState ->
                    div(className = "d-grid gap-2 col-6 mx-auto") {
                        val btnSignupText = if (uiState.formSubmitting) "Signing Up..." else "Signup"
                        button(btnSignupText, disabled = uiState.isSignupButtonDisabled) {
                            onClick {
                                val isFormValid = signupForm.validate()
                                if (!isFormValid) {
                                    return@onClick
                                }

                                val formData = signupForm.getData()

                                signupPageViewModel.trySend(
                                    SignupPageContract.Inputs.AttemptSignup(
                                        fullName = formData.fullName,
                                        email = formData.email,
                                        password = formData.password
                                    )
                                )
                            }
                            if (uiState.formSubmitting) {
                                span(className = "ml-2 spinner-border spinner-border-sm") {
                                    setAttribute("role", "status")
                                    setAttribute("aria-hidden", "true")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}