package com.nutrify.service

import com.nutrify.dto.RegisterUserRequest
import com.nutrify.entity.UserMetadataRow
import com.nutrify.repo.UserRepo

class UserService(private val userRepo: UserRepo) {

    fun registerUserMetadata(userRequest: RegisterUserRequest): String {
        //TODO: Main logic here for nutrition calculations plus any other metadata
        val user = UserMetadataRow(userRequest.id, userRequest.height, userRequest.weight, userRequest.gender, userRequest.activityLevel,  userRequest.goal, userRequest.age, userRequest.notes)
        return userRepo.insertUserMetadata(user);
    }
}