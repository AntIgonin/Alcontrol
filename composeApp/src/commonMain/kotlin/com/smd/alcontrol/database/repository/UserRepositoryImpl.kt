package com.smd.alcontrol.database.repository

import com.smd.alcontrol.database.dao.UserDao
import com.smd.alcontrol.database.model.User

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun addUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(): User? {
        return userDao.getUserById(1)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
