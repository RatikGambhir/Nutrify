package com.nutrify.service

import com.nutrify.dto.GenerationConfig
import com.nutrify.dto.RegisterUserRequest
import com.nutrify.entity.UserMetadata
import com.nutrify.lib.clients.GeminiRestClient
import com.nutrify.lib.factories.PromptFactory
import com.nutrify.lib.prompt_extensions.toPromptVariables
import com.nutrify.repo.UserRepo


class UserService(private val userRepo: UserRepo, private val client: GeminiRestClient) {

    suspend fun registerUserMetadata(userRequest: RegisterUserRequest): String? {
        val recommendations = genRecommendations(userRequest)
        val user = UserMetadata(
            userRequest.userId,
            userRequest.height,
            userRequest.weight,
            userRequest.gender,
            userRequest.activityLevel,
            userRequest.age,
        )
        val result = userRepo.insertUserMetadata(user);

        if(result === "Mutation Success") {
            return recommendations
        }
        return null
    }

    suspend fun genRecommendations(
        userRequest: RegisterUserRequest,
        config: GenerationConfig? = null,
        systemInstruction: String? = null
    ): String {
        val prompt = PromptFactory.getPromptWithVariables("registerUserMetadata", userRequest.toPromptVariables())
        val result = client.askQuestion(prompt, config, systemInstruction)
        return result
    }

   suspend fun saveUserRecommendations(userId: String): UserMetadata? {
       return null
   }
}