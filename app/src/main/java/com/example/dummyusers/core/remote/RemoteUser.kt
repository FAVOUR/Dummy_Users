package com.example.dummyusers.core.remote

data class RemoteUser(
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
