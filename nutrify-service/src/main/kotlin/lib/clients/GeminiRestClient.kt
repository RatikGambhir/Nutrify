package com.nutrify.lib.clients


import com.nutrify.lib.factories.PromptFactory
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import kotlinx.serialization.json.Json

class GeminiRestClient(val apiKey: String, val baseUrl: String = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent") : RestClient(apiKey, baseUrl) {

    override fun getFood(): String {
        return searchRecipes("hello")
    }

    suspend fun askQuestion(prompt: String): String {
        val jsonBody = Json.encodeToString(
            mapOf(
                "contents" to listOf(
                    mapOf(
                        "parts" to listOf(
                            mapOf("text" to prompt)
                        )
                    )
                )
            )
        )

        val statement = client.preparePost() {
            setBody(
                jsonBody
        ) }
        val response = statement.execute()
        val result = response.body<Any>()
        println(result)
        return result.toString()
    }

}