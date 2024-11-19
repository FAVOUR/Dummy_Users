package com.example.core.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {

    @GET("api/users")
    suspend fun getAllUsers(): Response<List<RemoteUser>>

    @GET("api/users/{userId}")
    suspend fun userById(@Path("userId") userId: String): Response<RemoteUser>
}
