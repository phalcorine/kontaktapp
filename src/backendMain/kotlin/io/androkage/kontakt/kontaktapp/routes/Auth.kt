package io.androkage.kontakt.kontaktapp.routes

import arrow.core.continuations.either
import io.androkage.kontakt.kontaktapp.dto.AuthLoginRequestDto
import io.androkage.kontakt.kontaktapp.dto.AuthRegisterRequestDto
import io.androkage.kontakt.kontaktapp.endpoints.IAuthEndpointService
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.androkage.kontakt.kontaktapp.plugins.AUTH_JWT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/*
fun Route.authRoutes() {

    val authEndpointService by inject<IAuthEndpointService>()

    route("auth") {

        post("/login") {
            either {
                val requestBody = runCatching {
                    call.receive<AuthLoginRequestDto>()
                }.getOrNull() ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val response = authEndpointService.login(requestBody).bind()

                call.respond(
                    status = HttpStatusCode.OK,
                    response
                )
            }.mapLeft {
                handleError(it)
            }
        }

        post("/register") {
            either {
                val requestBody = runCatching {
                    call.receive<AuthRegisterRequestDto>()
                }.getOrNull() ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val response = authEndpointService.signup(requestBody).bind()

                call.respond(
                    status = HttpStatusCode.OK,
                    response
                )
            }.mapLeft {
                handleError(it)
            }
        }

        authenticate(AUTH_JWT) {
            get("/check-auth") {
                either {
                    val response = authEndpointService.checkAuth().bind()

                    call.respond(
                        status = HttpStatusCode.OK,
                        response
                    )
                }.mapLeft {
                    handleError(it)
                }
            }
        }

    }
}*/
