package io.androkage.kontakt.kontaktapp.app.auth.pages.login

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
data class LoginFormDto(
    val email: String = "",
    val password: String = ""
)

fun Container.loginPage() : KoinComponent = object : KoinComponent {
    private val loginPageViewModel by inject<LoginPageViewModel>()
    init {
        authLayout {
            div {
                div(className = "text-center") {
                    h1(className = "h3 fw-normal") {
                        +"Login"
                    }
                }
                val loginForm = formPanel<LoginFormDto> {
                    add(
                        LoginFormDto::email,
                        Text(label = "Email", type = InputType.EMAIL) {
                            placeholder = "Login Email..."
                        },
                        required = true
                    )
                    add(
                        LoginFormDto::password,
                        Text(label = "Password", type = InputType.PASSWORD) {
                            placeholder = "Login Password..."
                        },
                        required = true
                    )
                }
                div(className = "justify-content-between align-items-center").bind(loginPageViewModel) { pageState ->
                    div(className = "d-grid gap-2 col-6 mx-auto") {
                        val btnLoginText = if (pageState.formSubmitting) "Logging In..." else "Login"
                        button(btnLoginText, disabled = pageState.isLoginButtonDisabled) {
                            onClick {
                                val isFormValid = loginForm.validate()
                                if (!isFormValid) {
                                    return@onClick
                                }

                                val formData = loginForm.getData()

                                loginPageViewModel.trySend(
                                    LoginPageContract.Inputs.AttemptLogin(
                                        email = formData.email,
                                        password = formData.password
                                    )
                                )
                            }
                            if (pageState.formSubmitting) {
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