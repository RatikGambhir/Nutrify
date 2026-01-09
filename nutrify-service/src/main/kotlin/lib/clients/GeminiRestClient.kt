package com.nutrify.lib.clients


import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class GeminiRestClient(val apiKey: String, val baseUrl: String = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent") : RestClient(apiKey, baseUrl) {

    override fun getFood(): String {
        return searchRecipes("hello")
    }

    suspend fun askQuestion(prompt: String): String {
        val response = client.post { setBody(
            mapOf("contents" to listOf(
                mapOf("parts" to listOf(mapOf("text" to "What is the capital of France?")))
            ))
        ) }
        val result = response.body<Map<String, Any>>()
        println(result)
        return result.toString()
    }

}