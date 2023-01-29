package com.tylllenka.data

import com.tylllenka.data.models.User


interface UserDataSource {

    suspend fun getUserByUsername(username: String): User?

    suspend fun insertUser(user: User): Boolean
}