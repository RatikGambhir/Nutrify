package com.nutrify.lib.factories

import com.zaxxer.hikari.HikariDataSource
import kotlin.use


data class Result(val body: Any?, val error: Exception? )

class SupabaseManager(val dataSource: HikariDataSource, val sqlFactory: SQLFactory) {


    private fun getSQLQuery(queryName: String): String? {
        return sqlFactory.get(queryName)
    }


    fun mutate(queryName: String, params: List<Any>): Result {
        val sqlQuery = getSQLQuery(queryName) ?: return Result(null, Exception("SQL Query not found"))
        dataSource.connection.use { connection ->
            connection.prepareStatement(sqlQuery).use { statement ->
                params.forEachIndexed { index, param ->
                    statement.setObject(index + 1, param)
                }

                try {
                    statement.executeQuery().use { resultSet ->
                        val resultVal = if (resultSet.next()) resultSet.getObject(1) else "No result found"
                        return Result(resultVal, null)
                    }
                } catch (e: Exception) {
                    println(e.message)
                    println(e.cause)
                    return Result(null, e)
                }
            }
        }
    }

    fun <T> query(queryName: String, params: List<Any> = emptyList(), mapper: (java.sql.ResultSet) -> T): Result {
        val sqlQuery = getSQLQuery(queryName) ?: return Result(null, Exception("SQL Query not found"))
        dataSource.connection.use { connection ->
            connection.prepareStatement(sqlQuery).use { statement ->
                params.forEachIndexed { index, param ->
                    statement.setObject(index + 1, param)
                }

                try {
                    statement.executeQuery().use { resultSet ->
                        val results = mutableListOf<T>()
                        while (resultSet.next()) {
                            results.add(mapper(resultSet))
                        }
                        return Result(results, null)
                    }
                } catch (e: Exception) {
                    println(e.message)
                    println(e.cause)
                    return Result(null, e)
                }
            }
        }
    }
}