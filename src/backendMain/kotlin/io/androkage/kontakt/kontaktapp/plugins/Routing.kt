package io.androkage.kontakt.kontaktapp.plugins

import io.androkage.kontakt.kontaktapp.endpoints.IAuthCheckerEndpointService
import io.androkage.kontakt.kontaktapp.endpoints.IAuthEndpointService
import io.androkage.kontakt.kontaktapp.endpoints.IContactEndpointService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.getServiceManager
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    install(Compression)

    /*install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }*/

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader("MyCustomHeader")
        allowHost("localhost:3000", schemes = listOf("http", "https"))
//        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    routing {
        applyRoutes(getServiceManager<IAuthEndpointService>())
        authenticate(AUTH_JWT) {
            applyRoutes(getServiceManager<IAuthCheckerEndpointService>())
            applyRoutes(getServiceManager<IContactEndpointService>())
        }

        route("/api") {
            /*authRoutes()
            contactRoutes()*/
        }
    }
}