package com.smd.alcontrol.database.repository

import com.smd.alcontrol.database.model.User

interface UserRepository {
    suspend fun addUser(user: User)
    suspend fun getUser(): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}