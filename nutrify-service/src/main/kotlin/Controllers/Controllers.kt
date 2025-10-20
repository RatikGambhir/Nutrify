package com.nutrify

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*

fun Application.configureControllers(dataSource: HikariDataSource) {
    configureExerciseController(dataSource)
}
