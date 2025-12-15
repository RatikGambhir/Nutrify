package com.nutrify

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import org.slf4j.event.*

fun Application.configureRouting() {
    // configure services here, then pass to controllers
    // We're going to need to create an instance container to manage our dependencies, create
    // objects, and wire them up
    install(SSE)

    configureExerciseController();
    configureUserControllers();

    routing {
        get("/") { call.respondText("Hello World!") }
        sse("/hello") { send(ServerSentEvent("world")) }
    }
}
