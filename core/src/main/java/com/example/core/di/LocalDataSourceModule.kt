package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.UserLocalDatasourceImpl
import com.example.core.local.UserDao
import com.example.core.local.UserDatabase
import com.example.data.local.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext, UserDatabase::class.java, "User.db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserLocalDataSource(dataSource: UserLocalDatasourceImpl): UserLocalDataSource

}
