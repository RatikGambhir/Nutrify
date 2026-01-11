package com.nutrify.entity

import kotlinx.serialization.Serializable
import java.time.Instant

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

@Serializable
data class Meal(
    val id: String,
    val userId: String,
    val mealType: String,
    val consumedAt: String,
    val notes: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class Food(
    val id: String,
    val name: String,
    val brand: String? = null,
    val category: String? = null,
    val servingSize: Double? = null,
    val servingUnit: String? = null,
    val nutritionPer100g: String? = null,
    val caloriesPer100g: Double? = null,
    val proteinGPer100g: Double? = null,
    val carbGPer100g: Double? = null,
    val fatGPer100g: Double? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class MealItem(
    val id: String,
    val mealId: String,
    val foodId: String? = null,
    val customName: String? = null,
    val quantity: Double,
    val unit: String,
    val grams: Double? = null,
    val calories: Double,
    val proteinG: Double,
    val carbG: Double,
    val fatG: Double,
    val nutritionJson: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
