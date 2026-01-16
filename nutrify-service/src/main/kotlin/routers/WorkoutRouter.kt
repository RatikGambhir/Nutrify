package com.nutrify

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
//TODO: Add controller class here

fun Application.configureExerciseRoutes() {
    routing { get("/") { call.respondText("Hello, World!") } }
}
