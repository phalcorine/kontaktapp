package io.androkage.kontakt.kontaktapp.app.layout.authLayout

import io.androkage.kontakt.kontaktapp.app.layout.shared.appHeader.appHeader
import io.kvision.core.Container
import io.kvision.html.main
import org.koin.core.component.KoinComponent

fun Container.authLayout(content: Container.() -> Unit) : KoinComponent = object : KoinComponent {
    init {
        main(className = "auth-layout w-100 m-auto") {
            content()
        }
    }
}