package io.androkage.kontakt.kontaktapp.app.layout.mainLayout

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

fun Container.mainLayout(headerTitle: String, content: Container.() -> Unit) : KoinComponent = object : KoinComponent {
    private val routerViewModel by inject<AppRouterViewModel>()

    init {
        div().bind(routerViewModel) { appRouter ->
            val currentRoute = appRouter.backstack.last()
            val currentRouteUrl = currentRoute.originalDestinationUrl

            header(className = "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow") {
                link("App Name", className = "navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6") {

                }
                button("", className = "navbar-toggler position-absolute d-md-none collapsed") {
                    setAttribute("data-bs-collapse", "collapse")
                    setAttribute("data-bs-target", "#sidebarMenu")
                    setAttribute("aria-controls", "sidebarMenu")
                    setAttribute("aria-expanded", "false")
                    setAttribute("aria-label", "Toggle navigation")
                    span(className = "navbar-toggler-icon") { }
                }
                input(className = "form-control form-control-dark w-100 rounded-0 border-0", type = InputType.TEXT) {
                    placeholder = "Search"
                    setAttribute("aria-label", "Search")
                }
                div(className = "navbar-nav") {
                    div(className = "nav-item text-nowrap") {
                        link("Sign Out", className = "nav-link px-3") {
                            url = "#"
                        }
                    }
                }
            }

            div(className = "container-fluid") {
                div(className = "row") {
                    nav(className = "col-md-3 col-lg-2 d-md-block bg-light sidebar collapse") {
                        id = "sidebarMenu"
                        div(className = "position-sticky pt-3 sidebar-sticky") {
                            ul(className = "nav flex-column") {
                                li(className = "nav-item") {
                                    link("Dashboard", className = "nav-link", icon = "bi bi-house-door") {
                                        setAttribute("aria-current", "page")
                                        url = "#${AppRouter.Home.directions().build()}"
                                        onClick {
                                            routerViewModel.trySend(
                                                RouterContract.Inputs.GoToDestination(
                                                    AppRouter.Home
                                                        .directions()
                                                        .build()
                                                )
                                            )
                                        }
                                        if (currentRouteUrl == AppRouter.Home.directions().build()) {
                                            addCssClass("active")
                                        }
                                    }
                                }
                                li(className = "nav-item") {
                                    link("Contacts", className = "nav-link", icon = "bi bi-file-earmark") {
                                        url = "#${AppRouter.ContactList.directions().build()}"
                                        onClick {
                                            routerViewModel.trySend(
                                                RouterContract.Inputs.GoToDestination(
                                                    AppRouter.ContactList
                                                        .directions()
                                                        .build()
                                                )
                                            )
                                        }
                                        if (currentRouteUrl == AppRouter.ContactList.directions().build()) {
                                            addCssClass("active")
                                        }
                                    }
                                }
                                li(className = "nav-item") {
                                    link("Tasks", className = "nav-link", icon = "bi bi-list-task") {
                                        url = "#"
                                    }
                                }
                            }

                            h6(className = "sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase") {
                                span { +"Misc" }
                                link("", className = "link-secondary", icon = "bi bi-plus-circle") {
                                    setAttribute("aria-label", "Add a new report")
                                    url = "#"
                                }
                            }

                            ul(className = "nav flex-column mb-2") {
                                li(className = "nav-item") {
                                    link("Preferences", className = "nav-link", icon = "bi bi-gear") {
                                        url = "#"
                                    }
                                }
                            }
                        }
                    }

                    main(className = "col-md-9 ms-sm-auto col-lg-10 px-md-4") {
                        div(className = "d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom") {
                            h1(className = "h2") { +headerTitle }
                        }

                        content()
                    }
                }
            }
        }
    }
}