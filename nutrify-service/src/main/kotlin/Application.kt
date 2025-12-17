package com.nutrify

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.EmbeddedServer

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSerialization()



    val container = configureContainer()

//    ApplicationStopPreparing.run { val source = container.get<HikariDataSource>()
//        source.close()
//    }
    // configureMonitoring()
    configureHTTP()
    // configureSecurity()
    configureRouting(container)
}
