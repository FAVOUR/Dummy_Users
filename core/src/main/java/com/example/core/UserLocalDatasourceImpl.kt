package com.example.core

import com.example.data.local.User
import com.example.data.local.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDatasourceImpl(): UserLocalDataSource {

    override suspend fun observeUsers(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun obtainSpecificUser(id: String): User {
        TODO("Not yet implemented")
    }
}