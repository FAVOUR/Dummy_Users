package com.example.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeUsersProfile(): Flow<UserProfile>

    suspend fun getUserProfileById(): UserProfile

    suspend fun obtainAllUsersProfile(): List<UserProfile>
}
