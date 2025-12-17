package com.nutrify

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import java.sql.Connection
import org.slf4j.event.*

//class PostgresDB(hikariDataSource: HikariDataSource) {
//
//
//    fun <T: Any> query(block: (Connection) -> T): T {
//
//    }
//}

fun Application.configureDatabases(): HikariDataSource {
    val source = connectToPostgresPool()
    log.info("Connected to postgres! way to go ratik")

    return source
}

fun Application.connectToPostgresPool(): HikariDataSource {
    Class.forName("org.postgresql.Driver")

    val url = environment.config.property("postgres.session_pooler_url").getString()
    val user = environment.config.property("postgres.user").getString()
    val db_password = environment.config.property("postgres.password").getString()

    val config =
            HikariConfig().apply {
                jdbcUrl = url
                username = user
                password = db_password
                maximumPoolSize = 10
                isAutoCommit = true
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            }

    log.info("Connecting to Postgres via HikariCP pool at $url")
    return HikariDataSource(config)
}
