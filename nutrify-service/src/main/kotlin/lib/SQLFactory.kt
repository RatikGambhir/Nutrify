package com.nutrify.lib

class SQLFactory {

    private val queries: Map<String, String?> by lazy {
        mapOf(
            "registerUserMetadata" to loadSqlQuery("/sql/register_user_metadata.sql"),
            "Test" to loadSqlQuery("/sql/test.sql")
        )
    }

    private fun loadSqlQuery(path: String): String? {
        try {
            return object{}.javaClass.getResource(
                path
            )?.readText()
        } catch (e: Exception) {
            return null
        }
    }

    fun get(name: String): String? =
        queries[name]?.takeIf { it.isNotBlank() }
}