package com.nutrify

import com.nutrify.controllers.NutritionController
import com.nutrify.controllers.UserController
import com.nutrify.routers.configureNutritionRoutes
import com.nutrify.routers.configureUserRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*

fun Application.configureRouting(container: Container) {
    // configure services here, then pass to controllers
    // We're going to need to create an instance container to manage our dependencies, create
    // objects, and wire them up
    install(SSE)

   // configureExerciseRoutes();
    configureUserRoutes(container.get<UserController>());
    configureNutritionRoutes(container.get<NutritionController>())

//    routing {
//        get("/") { call.respondText("Hello World!") }
//        sse("/hello") { send(ServerSentEvent("world")) }
//    }
}
