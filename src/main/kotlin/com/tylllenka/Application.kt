package com.tylllenka

import io.ktor.server.application.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import com.tylllenka.data.MongodbUserDataSource
import com.tylllenka.plugins.*
import com.tylllenka.security.hashing.SHA256HashingService
import com.tylllenka.security.token.JwtTokenService
import com.tylllenka.security.token.TokenConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
//    val mongodbPassword = System.getenv("MONGODB_PASSWORD")
    val dbName = "project-server"
    val db = KMongo.createClient(
        connectionString = ""
    )
        .coroutine
        .getDatabase(dbName)

    val userDataSource = MongodbUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 30L * 1000L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(
        userDataSource = userDataSource,
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}