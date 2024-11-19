package com.example.dummyusers.data

import com.example.dummyusers.core.di.DefaultDispatcher
import com.example.dummyusers.data.local.UserLocalDataSource
import com.example.dummyusers.data.remote.UserRemoteDataSource
import com.example.dummyusers.domain.UserProfile
import com.example.dummyusers.domain.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepositoryImp @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun observeUsersProfile(): Flow<List<UserProfile>> {
        return localDataSource.observeUsers().map { users ->
            withContext(dispatcher) {
                users.toUserProfile()
            }
        }
    }

    override suspend fun getUserProfileById(
        id: String,
        makeNetworkCallFirst: Boolean
    ): UserProfile? {
        if (makeNetworkCallFirst) {
            refresh()
        }
        return localDataSource.obtainSpecificUser(id)?.toUserProfile()
    }

    override suspend fun obtainAllUsersProfile(makeNetworkCallFirst: Boolean): List<UserProfile> {
        if (makeNetworkCallFirst) {
            refresh()
        }

        return localDataSource.obtainUserData().map { users ->
            withContext(dispatcher) {
                users.toUserProfile()
            }
        }
    }

    override suspend fun saveUserProfiles(userProfile: List<UserProfile>) {
        val userData = userProfile.toUserData()
        localDataSource.saveUserData(userData)
    }

    override suspend fun refresh() {
        withContext(dispatcher) {
            val remoteUsers = remoteDataSource.fetchUsers()
            localDataSource.deleteUserData()
            remoteUsers?.let { userInfo ->
                localDataSource.saveUserData(userInfo.toUserData())
            }
        }
    }
}
