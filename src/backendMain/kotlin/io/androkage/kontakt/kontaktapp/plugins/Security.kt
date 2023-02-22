package io.androkage.kontakt.kontaktapp.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.androkage.kontakt.kontaktapp.dto.ApiErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

const val AUTH_JWT = "auth-jwt"
const val JWT_CLAIM_PRINCIPAL = "principal"

fun Application.configureSecurity(jwtConfig: JwtConfig) {

    install(Authentication) {
        jwt(AUTH_JWT) {
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.domain)
                    .build()
            )

            validate {credential ->
                if (credential.payload.audience.contains(jwtConfig.audience) && credential.payload.getClaim(
                        JWT_CLAIM_PRINCIPAL).asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    // Probably respond with an Unauthorized HTTP Response
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    ApiErrorResponse("The access token provided is invalid or has expired!")
                )
            }
        }
    }
}