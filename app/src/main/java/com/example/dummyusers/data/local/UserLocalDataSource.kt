package com.example.dummyusers.data.local

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    fun observeUsers(): Flow<List<UsersData>>

    suspend fun obtainSpecificUser(id: Int): UsersData?

    suspend fun obtainUserData(): List<UsersData>

    suspend fun saveUserData(usersData: List<UsersData>)

    suspend fun saveAUserData(usersData: UsersData)

    suspend fun deleteUserData()
}
