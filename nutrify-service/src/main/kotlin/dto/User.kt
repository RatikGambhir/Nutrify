package com.nutrify.dto
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(val id: String, val userId: String, val height: Int, val weight: Int, val gender: Boolean, val age: Int ,val activityLevel: String, val goal: String, val notes: String )
