package com.example.dummyusers.data.remote

data class UserInfo(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val homeDetails: HomeDetails
)

data class HomeDetails(
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
)
