package com.nutrify

import com.nutrify.lib.SQLFactory
import com.nutrify.lib.SupabaseManager
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import java.sql.Connection
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun Application.configureContainer(): Container {
    val dataSource = configureDatabases()
    val container = Container()
    val SQLFactory = SQLFactory()
    container.bindSingleton(SupabaseManager::class, SupabaseManager(dataSource,SQLFactory))
    return container
}

class Container {

    private val beans = mutableMapOf<KClass<*>, Any>()

    fun bindSingleton(classname: KClass<*>, instance: Any) {
        beans[classname]?.let {
            return;
        } ?: run {
            beans[classname] = instance
        }
    }

    inline fun <reified T: Any> get(): T {
        return get(T::class)
    }

    fun <T: Any> get(classname: KClass<*>): T {
        val instance = beans[classname]
        @Suppress("UNCHECKED_CAST")
        instance?.let { return it as T }
        val created = createInstance(classname)
        beans[classname] = created
        @Suppress("UNCHECKED_CAST")
        return created as T;

    }

     fun <T: Any> createInstance(classname: KClass<T>): T {
        val constructor = classname.primaryConstructor!!
        val parameters = constructor.parameters.associateWith{param ->
            val type = param.type.classifier as KClass<*>
            getInstance(type)

        }

         return constructor.callBy(parameters)

        }

    fun getInstance(classname: KClass<*>): Any {
        @Suppress("UNCHECKED_CAST")
        return get(classname as KClass<Any>)
    }
    }

