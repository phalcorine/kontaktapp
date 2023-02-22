package io.androkage.kontakt.kontaktapp

import com.copperleaf.ballast.navigation.routing.*
import io.androkage.kontakt.kontaktapp.app.AppRouter
import io.androkage.kontakt.kontaktapp.app.AppRouterViewModel
import io.androkage.kontakt.kontaktapp.app.auth.pages.login.loginPage
import io.androkage.kontakt.kontaktapp.app.auth.pages.register.signupPage
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd.contactAddPage
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail.contactDetailPage
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList.contactListPage
import io.androkage.kontakt.kontaktapp.app.initializeKoin
import io.androkage.kontakt.kontaktapp.app.landing.pages.landingPage.landingPage
import io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader.appHeader
import io.androkage.kontakt.kontaktapp.repository.auth.AuthRepository
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.BootstrapModule
import io.kvision.BootstrapCssModule
import io.kvision.DatetimeModule
import io.kvision.RichTextModule
import io.kvision.TomSelectModule
import io.kvision.BootstrapUploadModule
import io.kvision.ImaskModule
import io.kvision.ToastifyModule
import io.kvision.FontAwesomeModule
import io.kvision.BootstrapIconsModule
import io.kvision.PrintModule
import io.kvision.TabulatorModule
import io.kvision.TabulatorCssBootstrapModule
import io.kvision.html.div
import io.kvision.module
import io.kvision.panel.root
import io.kvision.startApplication
import io.kvision.state.bind
import io.kvision.toast.Toast
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application(), KoinComponent {

    init {
        io.kvision.require("css/auth.css")
        io.kvision.require("css/dashboard.css")
    }

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
            val router by inject<AppRouterViewModel>()
            val authRepo by inject<AuthRepository>()

            div().bind(router) { appRouterState ->
                val routerState = appRouterState.backstack

                appHeader()

                routerState.renderCurrentDestination(
                    route = { appRouter ->
                        when (appRouter) {
                            AppRouter.Home -> {
                                landingPage()
                            }
                            AppRouter.Login -> {
                                loginPage()
                            }
                            AppRouter.Signup -> {
                                signupPage()
                            }
                            AppRouter.ContactList -> {
                                contactListPage()
                            }
                            AppRouter.ContactAdd -> {
                                contactAddPage()
                            }
                            AppRouter.ContactDetail -> {
                                val contactUid by stringPath("uid")
                                contactDetailPage(contactUid)
                            }
                        }
                    },
                    notFound = {
                        Toast.danger("Route not found!")
                        console.log("Route not found! Going home...")
                        router.trySend(
                            RouterContract.Inputs.GoToDestination(
                                AppRouter.Home.directions().build()
                            )
                        )
                    }
                )
            }

            // Initialize Auth Repo
            authRepo.initialize()
        }
    }
}

fun main() {
    initializeKoin()

    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        DatetimeModule,
        RichTextModule,
        TomSelectModule,
        BootstrapUploadModule,
        ImaskModule,
        ToastifyModule,
        FontAwesomeModule,
        BootstrapIconsModule,
        PrintModule,
        TabulatorModule,
        TabulatorCssBootstrapModule,
        CoreModule
    )
}
