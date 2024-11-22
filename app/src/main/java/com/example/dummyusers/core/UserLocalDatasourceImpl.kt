package com.example.dummyusers.core

import com.example.dummyusers.core.local.UserDao
import com.example.dummyusers.data.local.UserLocalDataSource
import com.example.dummyusers.data.local.UsersData
import com.example.dummyusers.data.toLocalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalDatasourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserLocalDataSource {

    override fun observeUsers(): Flow<List<UsersData>> {
        return userDao.observeAllUsers().map { users ->
            users.toUserData()
        }
    }

    override suspend fun obtainSpecificUser(id: Int): UsersData? {
        return userDao.obtainUser(id)?.toUserData()
    }

    override suspend fun obtainUserData(): List<UsersData> {
        return userDao.obtainAllUsers().toUserData()
    }

    override suspend fun saveUserData(usersData: List<UsersData>) {
        with(usersData.toLocalData()) {
            userDao.upsertUsers(this)
        }
    }

    override suspend fun saveAUserData(usersData: UsersData) {
        with(usersData.toLocalData()) {
            userDao.upsertUser(this)
        }
    }

    override suspend fun deleteUserData() {
        userDao.deleteAll()
    }
}
