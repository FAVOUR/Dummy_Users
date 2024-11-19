package com.example.core.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class LocalUser(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val userLocationData: UserLocationData
)

data class UserLocationData(
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
)
