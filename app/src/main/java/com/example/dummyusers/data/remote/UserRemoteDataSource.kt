package com.example.dummyusers.data.remote

interface UserRemoteDataSource {

    suspend fun fetchUsers(): List<UserInfo>?

    suspend fun searchUser(userId: String): UserInfo?

}
