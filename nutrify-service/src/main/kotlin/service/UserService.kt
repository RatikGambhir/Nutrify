package com.nutrify.service

import com.google.genai.Client
import com.nutrify.dto.RegisterUserRequest
import com.nutrify.entity.UserMetadataRow
import com.nutrify.lib.clients.GeminiRestClient
import com.nutrify.lib.factories.PromptFactory
import com.nutrify.repo.UserRepo
import io.ktor.server.application.ApplicationEnvironment


class UserService(private val userRepo: UserRepo, private val client: GeminiRestClient) {

    suspend fun registerUserMetadata(userRequest: RegisterUserRequest): String {
        //TODO: Main logic here for nutrition calculations plus any other metadata
        val rec = genRecommendations(userRequest)
        println(rec)
        return rec
//        val user = UserMetadataRow(userRequest.userId, userRequest.height, userRequest.weight, userRequest.gender, userRequest.activityLevel,  userRequest.goal, userRequest.age, userRequest.notes)
//        return userRepo.insertUserMetadata(user);
    }

    suspend fun genRecommendations(userRequest: RegisterUserRequest): String {
        val result = client.askQuestion("How do CPUs work")
        return result
    }

    suspend fun saveUserRecommendations(userRequest: RegisterUserRequest, recommendations: String) {

    }
}