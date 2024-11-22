package com.example.dummyusers.core.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao

interface UserDao {

    @Upsert
    fun upsertUsers(user: List<LocalUser>)

    @Upsert
    fun upsertUser(user: LocalUser)

    @Query("SELECT * FROM user")
    fun obtainAllUsers(): List<LocalUser>

    @Query("SELECT * FROM user")
    fun observeAllUsers(): Flow<List<LocalUser>>

    @Query("SELECT * FROM user WHERE id == :userId")
    fun obtainUser(userId: Int): LocalUser?

    @Query("DELETE FROM user")
    fun deleteAll()
}
