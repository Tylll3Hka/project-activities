package com.tylllenka.plugins

import com.tylllenka.data.UserDataSource
import com.tylllenka.routes.getSecretInfo
import com.tylllenka.routes.signIn
import com.tylllenka.routes.signUp
import com.tylllenka.security.hashing.HashingService
import com.tylllenka.security.token.JwtTokenService
import com.tylllenka.security.token.TokenConfig
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {

    routing {
        signUp<HashingService, Any>(hashingService = hashingService, userDataSource = userDataSource)
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        getSecretInfo()
    }
}
