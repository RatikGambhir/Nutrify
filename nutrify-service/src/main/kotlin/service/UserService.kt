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
        val user = UserMetadataRow(userRequest.userId, userRequest.height, userRequest.weight, userRequest.gender, userRequest.activityLevel,  userRequest.goal, userRequest.age, userRequest.notes)
        return userRepo.insertUserMetadata(user);
    }

    suspend fun genRecommendations(userRequest: RegisterUserRequest): String {
        // TODO: Here is where we send the client data to gemini, be sure to format prompt here
        val prompt = "Given these variables, generate a ideal workout and nutrition plan. For the workout plan, only generate how many carbs, fats, protein, " +
                "and for workouts, generate a 5 day plan in json. Here are the factors:" +
                "height: `${userRequest.height}`, weight: `${userRequest.weight}`, age: `${userRequest.age}`"
        return "Mutation Success"
    }
}