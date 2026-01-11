package com.nutrify.routers

import com.nutrify.controllers.NutritionController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing


fun Application.configureNutritionRoutes(nutritionController: NutritionController) {
    routing {
        get("/meal") {  }
    }
}