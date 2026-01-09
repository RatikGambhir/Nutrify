package com.nutrify.lib.clients

import com.nutrify.RestClient

class NutritionRestClient(
    apiKey: String,
    url: String = "https://api.spoonacular.com"
) : RestClient(apiKey, url)

{
    override fun getFood(): String {
        return searchRecipes("hello")
    }
}