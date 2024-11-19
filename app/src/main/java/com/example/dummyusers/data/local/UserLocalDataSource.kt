package com.example.dummyusers.data.local

import com.example.dummyusers.data.remote.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    fun observeUsers(): Flow<List<UsersData>>

    suspend fun obtainSpecificUser(id: String): UsersData?

    suspend fun obtainUserData(): List<UsersData>

    suspend fun saveUserData(usersData: List<UsersData>)

    suspend fun deleteUserData()
}
