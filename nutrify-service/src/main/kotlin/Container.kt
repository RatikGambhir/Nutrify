package com.nutrify

import com.nutrify.lib.SQLFactory
import com.nutrify.lib.SupabaseManager
import io.ktor.server.application.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

fun Application.genSingletons(): List<Any> {
    return listOf(configureDatabases(), configureRestClient())
}

fun Application.configureContainer(): Container {
    val supabaseManager = configureDatabases()
    val geminiClient = configureRestClient()
    val configs = genSingletons()
    val container = Container()
    container.bindSingleton(supabaseManager)
    container.bindSingleton( geminiClient)
    return container

}

class Container {

    private val beans = mutableMapOf<KClass<*>, Any>()


    fun bindAll(instances: List<Any>) {
        instances.forEach {
            val classname = it::class
            bindSingleton(it)
            val primaryConstructor = classname.primaryConstructor!!
            val beansToBind = primaryConstructor.parameters.filter { param ->
                param.type.classifier != String::class && param.type.classifier == Object::class
            }.map { param -> param.type.classifier as KClass<*> }
            bindAll(beansToBind)
        }
    }

    fun <T: Any> has(type: KClass<T>): T? {
        val instance = beans[type]
        @Suppress("UNCHECKED_CAST")
        instance?.let { return it as T }
        return null
    }



    fun bindSingleton(instance: Any) {
        beans[instance::class]?.let {
            return;
        } ?: run {
            beans[instance::class] = instance
        }
    }

    inline fun <reified T: Any> get(): T {
        return get(T::class)
    }

    fun <T: Any> get(classname: KClass<*>): T {
        val exists = has(classname)
        @Suppress("UNCHECKED_CAST")
        exists?.let { return exists as T}
        val created = createInstance(classname)
        beans[classname] = created
        @Suppress("UNCHECKED_CAST")
        return created as T;
    }

     fun <T: Any> createInstance(classname: KClass<T>): T {
        val constructor = classname.primaryConstructor!!
        val parameters = constructor.parameters.associateWith{param ->
            val classname = param.type.classifier as KClass<*>
            getInstance(classname)

        }
         return constructor.callBy(parameters)

        }

    fun getInstance(classname: KClass<*>): Any {
        @Suppress("UNCHECKED_CAST")
        return get(classname as KClass<Any>)
    }
    }

