package com.nutrify.routers

import com.nutrify.controllers.UserController
import com.nutrify.dto.RegisterUserRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing




fun Application.configureUserRoutes(userController: UserController) {
    routing {
        post("/register") {
            val user = call.receive<RegisterUserRequest>()
            val registeredUser = userController.registerUserMetadata(user)
            if(registeredUser === "Mutation Success") {
                call.respond(HttpStatusCode.OK)
                return@post
            }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
