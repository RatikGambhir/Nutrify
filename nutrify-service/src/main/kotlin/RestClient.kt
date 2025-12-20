package com.nutrify
import com.google.genai.Client
import io.ktor.client.HttpClient
import io.ktor.server.application.*
import io.ktor.client.plugins.defaultRequest

import io.ktor.client.engine.cio.CIO


class GeminiClient(val client: Client) {

}

abstract class RestClient(apiKey: String, url: String = "") {
        private val client: HttpClient = HttpClient(CIO) {
                defaultRequest {
                        url("https://api.spoonacular.com") {
                                parameters.append("apiKey", apiKey)
                        }
                }
        }

        fun searchRecipes(query: String): String {
                return ""
        }

        abstract fun getFood(): String
}

class GeminiRestClient(val apiKey: String, val url: String = "https://api.gemini.com") : RestClient(apiKey, url) {

        override fun getFood(): String {
                return searchRecipes("hello")
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

fun Application.configureRestClient(): GeminiClient {
        val client = Client.builder().apiKey(environment.config.property("api.gemini").getString())
                .build()
        return GeminiClient(client)
}

fun Application.configureClient(): List<RestClient> {
        val geminiKey = environment.config.property("api.gemini").getString()
        val nutritionAPIKey = environment.config.property("api.nutrition").getString()
        val geminiClient = GeminiRestClient(geminiKey)
        val nutritionAPIClient = NutritionAPIClient(nutritionAPIKey)
        return listOf(geminiClient, nutritionAPIClient)
}