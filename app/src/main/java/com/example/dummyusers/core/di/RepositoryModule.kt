package com.example.dummyusers.core.di

import com.example.dummyusers.data.UserRepositoryImp
import com.example.dummyusers.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImp): UserRepository
}
