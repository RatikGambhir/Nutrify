package com.nutrify.lib

import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.memberProperties

fun loadSqlQuery(path: String): String? {
    try {
        return object{}.javaClass.getResource(
            path
        )?.readText()
    } catch (e: Exception) {
        return null
    }
}

object SqlFactory {
    val registerUserMetadata = loadSqlQuery("/sql/register_user_metadata.sql")
}

class SQLFactory {

    companion object {
        val registerUserMetadata = loadSqlQuery("/sql/register_user_metadata.sql")
    }

    fun genSQLQuery(queryName: String): String? {
        val companion = this::class.companionObjectInstance!!
        val query = this::class.companionObject!!.memberProperties.find { it.name == queryName }!!.getter.call(companion) as String
        if(query.isEmpty()) {
            return null
        }
        return query
    }
}