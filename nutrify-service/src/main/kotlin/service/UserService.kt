package com.nutrify.service

import com.nutrify.dto.GenerationConfig
import com.nutrify.dto.RegisterUserProfileRequest
import com.nutrify.entity.UserMetadata
import com.nutrify.lib.clients.GeminiRestClient
import com.nutrify.lib.factories.PromptFactory
import com.nutrify.lib.prompt_extensions.toPromptVariables
import com.nutrify.repo.UserRepo


class UserService(private val userRepo: UserRepo, private val client: GeminiRestClient) {

    suspend fun registerUserProfile(request: RegisterUserProfileRequest): String? {
        val recommendations = genRecommendations(request)
        val user = UserMetadata(
            request.userId,
            request.basicDemographics.height,
            request.basicDemographics.weight,
            request.basicDemographics.gender ?: request.basicDemographics.sex ?: "",
            request.activityLifestyle.activityLevel,
            request.basicDemographics.age,
        )
        val result = userRepo.insertUserMetadata(user)

        if(result === "Mutation Success") {
            return recommendations
        }
        return null
    }

    suspend fun genRecommendations(
        request: RegisterUserProfileRequest,
        config: GenerationConfig? = null,
        systemInstruction: String? = null
    ): String {
        val prompt = PromptFactory.getPromptWithVariables("registerUserMetadata", request.toPromptVariables())
        val result = client.askQuestion(prompt, config, systemInstruction)
        return result
    }

   suspend fun saveUserRecommendations(userId: String): UserMetadata? {
       return null
   }
}