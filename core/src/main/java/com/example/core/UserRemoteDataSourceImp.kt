package com.example.core

import com.example.core.remote.UsersApi
import com.example.data.remote.UserInfo
import com.example.data.remote.UserRemoteDataSource
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class UserRemoteDataSourceImp @Inject constructor(
    private val usersApi: UsersApi,
    private val retrofit: Retrofit,
) : UserRemoteDataSource {
    override suspend fun fetchUsers(): List<UserInfo>? {
        return usersApi.getAllUsers().extractData(retrofit)?.toUserInfo()
    }

    override suspend fun searchUser(userId: String): UserInfo? {
        return usersApi.userById(userId).extractData(retrofit)?.toUserInfo()
    }

}


inline fun <reified T> Response<T>.extractData(retrofitClient: Retrofit): T? {
    return with(this) {
        if (isSuccessful) {
            body()
        } else {
            val converter =
                retrofitClient.responseBodyConverter<T>(T::class.java, arrayOfNulls(0))
            errorBody()?.let { converter.convert(it) }
        }
    }
}