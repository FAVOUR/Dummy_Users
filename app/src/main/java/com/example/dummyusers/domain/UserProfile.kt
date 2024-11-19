package com.example.dummyusers.domain

class UserProfile(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val userLocation: UserLocation
)

data class UserLocation(
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
)
