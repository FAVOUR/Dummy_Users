package com.example.dummyusers.core

import com.example.dummyusers.data.remote.UserInfo
import com.example.dummyusers.data.remote.UserRemoteDataSource

class UserRemoteDataSourceFake(
    var userInfo: MutableList<UserInfo>? = mutableListOf()
) :
    UserRemoteDataSource {

    override suspend fun fetchUsers(): List<UserInfo>? {
        return userInfo ?: throw Exception("UserInfo is null")
    }

    override suspend fun searchUser(userId: String): UserInfo? {
        try {
            return userInfo?.firstOrNull { it.id == userId.toInt() }
        } catch (e: Throwable) {
            throw Exception("Wrong ID")
        }
    }
}
