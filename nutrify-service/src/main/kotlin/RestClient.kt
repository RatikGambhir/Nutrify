package com.nutrify
import com.google.genai.Client
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.server.application.*
import io.ktor.client.plugins.defaultRequest

import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody


//class GeminiClient(val client: Client) {
//
//}

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

class NutritionAPIClient(
        apiKey: String,
        url: String = "https://api.spoonacular.com"
) : RestClient(apiKey, url)

{
        override fun getFood(): String {
                return searchRecipes("hello")
        }
}



fun Application.configureRestClient(): List<RestClient> {
        val geminiKey = environment.config.property("api.gemini").getString()
        val nutritionAPIKey = environment.config.property("api.nutrition").getString()
        val geminiClient = GeminiRestClient(geminiKey)
        val nutritionAPIClient = NutritionAPIClient(nutritionAPIKey)
        return listOf(geminiClient, nutritionAPIClient)
}