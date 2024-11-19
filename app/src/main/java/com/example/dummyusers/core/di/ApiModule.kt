package com.example.dummyusers.core.di

import com.example.dummyusers.core.remote.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}
