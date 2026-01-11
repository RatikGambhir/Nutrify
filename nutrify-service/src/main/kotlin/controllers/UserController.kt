package com.nutrify.controllers

import com.nutrify.dto.RegisterUserRequest
import com.nutrify.service.UserService

class UserController(private val userService: UserService) {
    suspend fun registerUserMetadata(registerUserRequest: RegisterUserRequest): String {
        val thanks = userService.registerUserMetadata(
            registerUserRequest
        );
        return "Thanks for registering!"
    }
}

