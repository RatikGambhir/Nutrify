package com.nutrify

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*

fun Application.configureRouting() {
    // configure services here, then pass to controllers
    // We're going to need to create an instance container to manage our dependencies, create
    // objects, and wire them up
    install(SSE)

    configureExerciseRoutes();
    configureUserRoutes();

    routing {
        get("/") { call.respondText("Hello World!") }
        sse("/hello") { send(ServerSentEvent("world")) }
    }
}
