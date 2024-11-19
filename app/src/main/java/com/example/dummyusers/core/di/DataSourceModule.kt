package com.example.dummyusers.core.di

import com.example.dummyusers.core.UserLocalDatasourceImpl
import com.example.dummyusers.core.UserRemoteDataSourceImp
import com.example.dummyusers.data.local.UserLocalDataSource
import com.example.dummyusers.data.remote.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserLocalDataSource(dataSource: UserLocalDatasourceImpl): UserLocalDataSource

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(dataSource: UserRemoteDataSourceImp): UserRemoteDataSource
}
