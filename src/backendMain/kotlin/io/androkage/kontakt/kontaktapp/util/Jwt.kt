package io.androkage.kontakt.kontaktapp.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.impl.ClaimsHolder
import com.auth0.jwt.interfaces.Claim
import io.androkage.kontakt.kontaktapp.plugins.AppConfig
import io.androkage.kontakt.kontaktapp.plugins.JWT_CLAIM_PRINCIPAL
import io.androkage.kontakt.kontaktapp.plugins.JwtConfig
import io.ktor.server.application.*
import java.util.*

class JwtService(
    appConfig: AppConfig,
) {
    private val jwtConfig: JwtConfig = appConfig.jwt
    fun generateToken(userUid: String): String {

        return JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.domain)
            .withClaim(JWT_CLAIM_PRINCIPAL, userUid)
            .withExpiresAt(Date((System.currentTimeMillis() + 60000) * 60))
            .sign(Algorithm.HMAC256(jwtConfig.secret))
    }
}