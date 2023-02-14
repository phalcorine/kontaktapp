package io.androkage.kontakt.kontaktapp.api

import io.androkage.kontakt.kontaktapp.api.endpoints.ContactEndpointServiceImpl
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val apiUrl = "http://localhost:8080/api"

val httpClient = HttpClient(Js) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

val endpointModule = module {
    single<ContactEndpointService> { ContactEndpointServiceImpl }
}