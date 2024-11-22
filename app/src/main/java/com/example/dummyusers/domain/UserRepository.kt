package com.example.dummyusers.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeUsersProfile(): Flow<List<UserProfile>>

    suspend fun getUserProfileById(id: Int, makeNetworkCallFirst: Boolean = false): UserProfile?

    suspend fun obtainAllUsersProfile(
        makeNetworkCallFirst: Boolean = false,
        deleteExistingRecord: Boolean = false
    ): List<UserProfile>

    suspend fun saveUserProfiles(
        userProfile: List<UserProfile>,
        deleteExistingRecord: Boolean = false
    )

    suspend fun storeAUser(userProfile: UserProfile)

    suspend fun updateUsers(clearDatabase: Boolean)

    suspend fun deleteAllUsers()
}
