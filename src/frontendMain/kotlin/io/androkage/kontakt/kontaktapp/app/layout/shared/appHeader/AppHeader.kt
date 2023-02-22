package io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader

import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.state.bind
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Container.appHeader() : KoinComponent = object : KoinComponent {
    private val appHeaderViewModel by inject<AppHeaderViewModel>()
    private val routerViewModel by inject<AppRouterViewModel>()

    init {
        header(className = "navbar navbar-dark sticky-top bg-dark p-0 shadow") {
            link("Kontakt App", className = "navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6") {

            }
            button("", className = "navbar-toggler position-absolute d-md-none collapsed") {
                setAttribute("data-bs-collapse", "collapse")
                setAttribute("data-bs-target", "#sidebarMenu")
                setAttribute("aria-controls", "sidebarMenu")
                setAttribute("aria-expanded", "false")
                setAttribute("aria-label", "Toggle navigation")
                span(className = "navbar-toggler-icon") { }
            }
            div(className = "text-end").bind(appHeaderViewModel) { uiState ->
                if (uiState.isLoggedIn) {
                    button("Logout", className = "me-2", style = ButtonStyle.WARNING) {
                        onClick {
                            appHeaderViewModel.trySend(
                                AppHeaderContract.Inputs.LogOut
                            )
                        }
                    }
                } else {
                    button("Login", className = "me-2", style = ButtonStyle.OUTLINELIGHT) {
                        onClick {
                            routerViewModel.trySend(
                                RouterContract.Inputs.GoToDestination(
                                    AppRouter.Login
                                        .directions()
                                        .build()
                                )
                            )
                        }
                    }
                    button("Register", className = "me-2", style = ButtonStyle.WARNING) {
                        onClick {
                            routerViewModel.trySend(
                                RouterContract.Inputs.GoToDestination(
                                    AppRouter.Signup
                                        .directions()
                                        .build()
                                )
                            )
                        }
                    }
                }
            }
        }

        appHeaderViewModel.trySend(AppHeaderContract.Inputs.Initialize)
    }
}