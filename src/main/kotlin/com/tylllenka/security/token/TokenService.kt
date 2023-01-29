package com.tylllenka.security.token

interface TokenService {

    fun generate(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String
}