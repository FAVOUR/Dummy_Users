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
        id: Int,
        makeNetworkCallFirst: Boolean
    ): UserProfile? {
        if (makeNetworkCallFirst) {
            val remoteUser = remoteDataSource.searchUser(id.toString())
            remoteUser?.let {
                storeAUser(it.toUserProfile())
            }
        }
        return localDataSource.obtainSpecificUser(id)?.toUserProfile()
    }

    override suspend fun obtainAllUsersProfile(
        makeNetworkCallFirst: Boolean,
        deleteExistingRecord: Boolean
    ): List<UserProfile> {
        if (makeNetworkCallFirst) {
            updateUsers(deleteExistingRecord)
        }

        return localDataSource.obtainUserData().map { users ->
            withContext(dispatcher) {
                users.toUserProfile()
            }
        }
    }

    override suspend fun saveUserProfiles(
        userProfile: List<UserProfile>,
        deleteExistingRecord: Boolean
    ) {
        withContext(dispatcher) {
            val userData = userProfile.toUserData()

            if (deleteExistingRecord) {
                localDataSource.deleteUserData()
            }
            userData.let { userInfo ->
                localDataSource.saveUserData(userInfo)
            }
        }
    }

    override suspend fun storeAUser(userProfile: UserProfile) {
        val userData = userProfile.toUserData()
        localDataSource.saveAUserData(userData)
    }

    override suspend fun updateUsers(clearDatabase: Boolean) {
        val remoteUsers = remoteDataSource.fetchUsers()
        remoteUsers?.let { usersFetchedRemotely ->
            saveUserProfiles(usersFetchedRemotely.toUserProfile(), clearDatabase)
        }
    }

    override suspend fun deleteAllUsers() {
        localDataSource.deleteUserData()
    }
}
