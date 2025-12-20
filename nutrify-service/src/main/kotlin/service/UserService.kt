package com.nutrify.service

import com.google.genai.Client
import com.nutrify.GeminiClient
import com.nutrify.dto.RegisterUserRequest
import com.nutrify.entity.UserMetadataRow
import com.nutrify.repo.UserRepo
import io.ktor.server.application.ApplicationEnvironment


class UserService(private val userRepo: UserRepo, private val client: GeminiClient) {

    fun registerUserMetadata(userRequest: RegisterUserRequest): String {
        //TODO: Main logic here for nutrition calculations plus any other metadata
        val user = UserMetadataRow(userRequest.userId, userRequest.height, userRequest.weight, userRequest.gender, userRequest.activityLevel,  userRequest.goal, userRequest.age, userRequest.notes)
        return userRepo.insertUserMetadata(user);
    }
}