package com.nutrify

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSerialization()
    val dataSource = configureDatabases()

    // configureMonitoring()
    configureHTTP()
    // configureSecurity()
    configureRouting()
}
