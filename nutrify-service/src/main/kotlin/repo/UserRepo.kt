package com.nutrify.repo

import com.nutrify.entity.UserMetadataRow
import com.nutrify.lib.SupabaseManager
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.logError
import java.util.UUID

class UserRepo(private val supabaseManager: SupabaseManager) {


    fun insertUserMetadata(userMetadata: UserMetadataRow): String {
        val sql = """
            INSERT INTO user_metadata (user_id, height, weight, age, gender, activity_level, goal, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        val params: List<Any> = listOf(UUID.fromString("36d85d3d-99dd-405a-8b42-3182fad39dfe"), userMetadata.height,
            userMetadata.weight, userMetadata.gender, userMetadata.activityLevel,
            userMetadata.goal, userMetadata.notes)
        val result = supabaseManager.mutate(sql, params)
         if(result.body != null) {
             //TODO: Handle Mapping
            return ""
        } else {
            //TODO: Handle Error or missing value
            return "Hello"
         }

    }
}