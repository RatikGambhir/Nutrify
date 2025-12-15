package com.nutrify.routers

import com.nutrify.controllers.UserController
import io.ktor.server.application.*
import io.ktor.server.routing.get
import io.ktor.server.routing.routing




fun Application.configureUserRoutes(userController: UserController) {
    routing {
        get("/users") {  }
    }
}
