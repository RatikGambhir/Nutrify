package com.nutrify.controllers

import com.nutrify.service.UserService

class UserController(private val userService: UserService) {
    fun registerUser(): String {
        return userService.registerUser();
    }
}
