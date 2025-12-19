package com.nutrify.controllers

import com.nutrify.dto.RegisterUserRequest
import com.nutrify.service.UserService

class UserController(private val userService: UserService) {
    fun registerUserMetadata(registerUserRequest: RegisterUserRequest): String {
        return userService.registerUserMetadata(
            registerUserRequest
        );
    }
}

