package com.example.data.local

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun observeUsers(): Flow<List<UsersData>>

    suspend fun obtainSpecificUser(id: String): UsersData?
}
