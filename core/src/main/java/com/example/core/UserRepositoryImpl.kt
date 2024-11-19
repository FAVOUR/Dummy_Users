package com.example.core

import com.example.data.local.UserLocalDataSource
import com.example.data.remote.UserRemoteDataSource
import com.example.domain.UserProfile
import com.example.domain.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl constructor(
    userLocalDataSource: UserLocalDataSource,
    userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override fun observeUsersProfile(): Flow<UserProfile> {
    }

    override suspend fun getUserProfileById(): UserProfile {
        TODO("Not yet implemented")
    }

    override suspend fun obtainAllUsersProfile(): List<UserProfile> {
        TODO("Not yet implemented")
    }
}
