package com.example.dummyusers.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeUsersProfile(): Flow<List<UserProfile>>

    suspend fun getUserProfileById(id: String, makeNetworkCallFirst: Boolean = false): UserProfile?

    suspend fun obtainAllUsersProfile(makeNetworkCallFirst: Boolean = false): List<UserProfile>

    suspend fun saveUserProfiles(userProfile: List<UserProfile>)

    suspend fun refresh()
}
