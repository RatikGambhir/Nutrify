package com.nutrify

import io.ktor.server.application.*
import java.sql.Connection
import kotlin.reflect.KClass

fun Application.configureContainer(): Container {
    val dataSource = configureDatabases()
    val container = Container()
    container.bindSingleton(Connection::class, dataSource)
    return container
}

class Container {
    private val singletons = mutableMapOf<KClass<*>, Any>()

    fun bindSingleton(clazz: KClass<*>, instance: Any) {
        singletons[clazz] = instance
    }
}
