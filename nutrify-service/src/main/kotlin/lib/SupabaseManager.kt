package com.nutrify.lib

import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet
import java.util.UUID
import kotlin.use

data class MutationResult(val body: Any?, val error: Exception? )

class SupabaseManager(val dataSource: HikariDataSource) {


    fun mutate(sql: String, params: List<Any>): MutationResult {
        dataSource.connection.use { connection -> connection.prepareStatement(sql).use { statement ->
            params.forEachIndexed { index, any ->
                statement.setObject(index + 1, any)
            }

            try {
                statement.executeQuery().use {
                        result ->
                    val resultVal = if (result.next()) result.getObject(0) else "No result found"
                    val result = MutationResult(resultVal, null)
                    statement.close();
                    connection.close();
                    return result

                }
            } catch (e: Exception) {
                return MutationResult(null, e)
            }
        }}
    }
}