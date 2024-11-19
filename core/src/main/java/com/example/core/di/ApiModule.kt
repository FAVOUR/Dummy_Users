package com.example.core.di

import com.example.core.UserLocalDatasourceImpl
import com.example.core.remote.UsersApi
import com.example.data.local.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

}