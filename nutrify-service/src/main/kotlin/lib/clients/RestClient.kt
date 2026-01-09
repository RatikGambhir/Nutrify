package com.nutrify.lib.clients

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest

abstract class RestClient(apiKey: String, url: String) {
    val client: HttpClient = HttpClient(CIO) {
        defaultRequest {
            url(url) {
                headers.append("Content-Type", "application/json")
                headers.append("x-goog-api-key",apiKey)
            }
        }
    }

    fun searchRecipes(query: String): String {
        return ""
    }

    abstract fun getFood(): String
}