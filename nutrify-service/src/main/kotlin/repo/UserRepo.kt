package com.nutrify.repo

import com.nutrify.entity.UserMetadataRow
import com.nutrify.lib.SupabaseManager
import java.util.UUID

class UserRepo(private val supabaseManager: SupabaseManager) {


    fun insertUserMetadata(userMetadata: UserMetadataRow): String {
        val queryName = "registerUserMetadata"
        val userId = UUID.fromString(userMetadata.userId.trim())
        println(userId)
        println(queryName)
        val params: List<Any> = listOf(userId, userMetadata.height,
            userMetadata.weight, userMetadata.age, userMetadata.gender, userMetadata.activityLevel,
            userMetadata.goal, userMetadata.notes)
        val result = supabaseManager.mutate(queryName, params)
         if(result.body != null) {
             println("Success")
            return ""
        } else {
            println("Error")
            //TODO: Handle Error or missing value
            return "Hello"
         }

    }
}