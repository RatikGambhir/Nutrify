package com.nutrify.service

import com.nutrify.repo.UserRepo

class UserService(private val userRepo: UserRepo) {

    fun registerUser(): String {
        return userRepo.insertUser();
    }
}