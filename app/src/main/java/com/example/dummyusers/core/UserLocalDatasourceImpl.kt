package com.example.dummyusers.core

import com.example.dummyusers.core.local.UserDao
import com.example.dummyusers.data.local.UserLocalDataSource
import com.example.dummyusers.data.local.UsersData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
