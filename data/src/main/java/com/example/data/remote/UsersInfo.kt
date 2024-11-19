package com.example.data.remote

data class UserInfo(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address
)

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
)
