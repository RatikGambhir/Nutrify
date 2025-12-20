package com.nutrify

import com.nutrify.lib.SQLFactory
import com.nutrify.lib.SupabaseManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*



fun Application.configureDatabases(): SupabaseManager {
    val SQLFactory = SQLFactory()
    val source = connectToPostgresPool()
    log.info("Connected to postgres! way to go ratik")
    return SupabaseManager(source,SQLFactory)
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
