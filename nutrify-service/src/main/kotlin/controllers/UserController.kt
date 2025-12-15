package com.nutrify

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureUserControllers() {
    routing {
        get("/users") {  }
    }
}
