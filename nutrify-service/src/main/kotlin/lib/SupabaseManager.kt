package com.nutrify.lib

import com.zaxxer.hikari.HikariDataSource
import kotlin.use

data class MutationResult(val body: Any?, val error: Exception? )
data class QueryResult(val body: Any?, val error: Exception? )

class SupabaseManager(val dataSource: HikariDataSource, val sqlFactory: SQLFactory) {


    private fun getSQLQuery(queryName: String): String? {
        // TODO: Load SQL queries from SQLFactor
        return sqlFactory.genSQLQuery(queryName)

    }


    fun mutate(queryName: String, params: List<Any>): MutationResult {
        val sqlQuery = getSQLQuery(queryName) ?: return MutationResult(null, Exception("SQL Query not found"))
        dataSource.connection.use { connection -> connection.prepareStatement(sqlQuery).use { statement ->
            params.forEachIndexed { index, param ->
                statement.setObject(index + 1, param)
            }

            try {
                statement.executeQuery().use {
                        result ->
                    val resultVal = if (result.next()) result.getObject(1) else "No result found"
                    val result = MutationResult(resultVal, null)
                    connection.close();
                    return result

                }
            } catch (e: Exception) {
                println(e.message)
                println(e.cause)
                return MutationResult(null, e)
            }
        }}
    }

    // TODO: add query function (check for common logic as well)

//    fun query(queryName: String, params: List<Any>): QueryResult {
//        val sqlQuery = getSQLQuery(queryName) ?: return QueryResult(null, Exception("SQL Query not found"))
//        dataSource.connection.use { connection -> connection.prepareStatement(sqlQuery).use { statement ->
//            params.forEachIndexed { index, param ->
//                statement.setObject(index + 1, param)
//            }
//        }}
//    }
}