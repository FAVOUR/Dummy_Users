package com.example.data.remote

interface UserRemoteDataSource {

    fun fetchUsers(): List<UserInfo>

    fun searchUser(userId: String): UserInfo
}
