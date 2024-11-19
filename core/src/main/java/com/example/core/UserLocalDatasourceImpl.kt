package com.example.core

import com.example.core.local.UserDao
import com.example.data.local.UserLocalDataSource
import com.example.data.local.UsersData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserLocalDatasourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserLocalDataSource {

    override suspend fun observeUsers(): Flow<List<UsersData>> {
        return userDao.observeAllUsers().map { users ->
            users.toUserData()
        }
    }

    override suspend fun obtainSpecificUser(id: String): UsersData? {
        return userDao.obtainUser(id)?.toUserData()
    }
}