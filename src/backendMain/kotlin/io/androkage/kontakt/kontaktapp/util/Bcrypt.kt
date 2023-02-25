package io.androkage.kontakt.kontaktapp.util

import at.favre.lib.crypto.bcrypt.BCrypt

object BCryptUtils {
    private const val HASH_COST = 12

    fun hash(password: String): String {
        return BCrypt
            .withDefaults()
            .hashToString(HASH_COST, password.toCharArray())
    }

    fun verify(attemptedPassword: String, passwordHash: String): Boolean {
        return BCrypt
            .verifyer()
            .verify(attemptedPassword.toCharArray(), passwordHash)
            .verified
    }
}