package com.example.dummyusers.core.di

import android.content.Context
import androidx.room.Room
import com.example.dummyusers.BuildConfig
import com.example.dummyusers.core.local.UserDao
import com.example.dummyusers.core.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggingInterceptorModule {

    @Provides
    @Singleton
    fun provideDebugLoggingInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    private val CONNECT_TIMEOUT = 60L
    private val READ_TIMEOUT = 60L
    private val WRITE_TIMEOUT = 60L

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).apply {
                loggingInterceptor?.let {
                    addInterceptor(it)
                }
            }
            .build()
    }
}


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


