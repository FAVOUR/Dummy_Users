package com.example.dummyusers.data.local

data class UsersData(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val residence: Residence
)

data class Residence(
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
)
