package com.nutrify

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureExerciseController() {
    routing { get("/") { call.respondText("Hello, World!") } }
}
