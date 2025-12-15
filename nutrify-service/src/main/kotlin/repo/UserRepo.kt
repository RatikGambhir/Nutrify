package com.nutrify.repo

import com.zaxxer.hikari.HikariDataSource

class UserRepo(datasource: HikariDataSource) {


    fun insertUser(): String {
        return "hello world"
    }
}