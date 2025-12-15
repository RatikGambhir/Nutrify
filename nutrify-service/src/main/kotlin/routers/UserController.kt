package com.nutrify

import io.ktor.server.application.*
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

//TODO: Add controller class here

fun Application.configureUserRoutes() {
    routing {
        get("/users") {  }
    }
}
