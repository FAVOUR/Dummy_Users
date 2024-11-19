package com.example.core

import com.example.core.local.LocalUser
import com.example.core.local.UserLocationData
import com.example.core.remote.Address
import com.example.core.remote.RemoteUser
import com.example.data.local.Residence
import com.example.data.local.UsersData
import com.example.data.remote.HomeDetails
import com.example.data.remote.UserInfo

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
