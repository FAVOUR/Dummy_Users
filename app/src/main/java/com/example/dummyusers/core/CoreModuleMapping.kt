package com.example.dummyusers.core

import com.example.dummyusers.core.local.LocalUser
import com.example.dummyusers.core.local.UserLocationData
import com.example.dummyusers.core.remote.Address
import com.example.dummyusers.core.remote.RemoteUser
import com.example.dummyusers.data.local.Residence
import com.example.dummyusers.data.local.UsersData
import com.example.dummyusers.data.remote.HomeDetails
import com.example.dummyusers.data.remote.UserInfo

fun LocalUser.toUserData() =
    UsersData(
        id = id,
        name = name,
        username = username,
        email = email,
        residence = userLocationData.toResidence()
    )

@JvmName("LocalToUserData")
fun List<LocalUser>.toUserData() = map(LocalUser::toUserData)

private fun UserLocationData.toResidence(): Residence {
    return Residence(street = street, city = city, state = state, zipcode = zipcode)
}

fun RemoteUser.toUserInfo() =
    UserInfo(
        id = id,
        name = name,
        username = username,
        email = email,
        homeDetails = address.toHomeDetails()
    )

@JvmName("RemoteUserToUserInfo")
fun List<RemoteUser>.toUserInfo() = map(RemoteUser::toUserInfo)

private fun Address.toHomeDetails(): HomeDetails {
    return HomeDetails(street = street, city = city, state = state, zipcode = zipcode)
}
