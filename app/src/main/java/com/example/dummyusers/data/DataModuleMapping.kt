package com.example.dummyusers.data

import com.example.dummyusers.core.local.LocalUser
import com.example.dummyusers.core.local.UserLocationData
import com.example.dummyusers.data.local.Residence
import com.example.dummyusers.data.local.UsersData
import com.example.dummyusers.data.remote.HomeDetails
import com.example.dummyusers.data.remote.UserInfo
import com.example.dummyusers.domain.UserLocation
import com.example.dummyusers.domain.UserProfile

fun UsersData.toUserProfile() =
    UserProfile(
        id = id,
        name = name,
        username = username,
        email = email,
        userLocation = residence.toUserLocation()
    )

@JvmName("UserDataToUserProfile")
fun List<UsersData>.toUserProfile() = map(UsersData::toUserProfile)

private fun Residence.toUserLocation(): UserLocation {
    return UserLocation(street = street, city = city, state = state, zipcode = zipcode)
}

fun UserProfile.toUserData() =
    UsersData(
        id = id,
        name = name,
        username = username,
        email = email,
        residence = userLocation.toResidence()
    )

@JvmName("UserProfileToUserData")
fun List<UserProfile>.toUserData() = map(UserProfile::toUserData)

private fun UserLocation.toResidence(): Residence {
    return Residence(street = street, city = city, state = state, zipcode = zipcode)
}

private fun UsersData.toLocalData() = LocalUser(
    id = id,
    name = name,
    username = username,
    email = email,
    userLocationData = residence.toUserLocationData()
)

@JvmName("UserDataToLocalData")
fun List<UsersData>.toLocalData() = map(UsersData::toLocalData)

private fun Residence.toUserLocationData(): UserLocationData {
    return UserLocationData(street = street, city = city, state = state, zipcode = zipcode)
}

fun UserInfo.toUserData() =
    UsersData(
        id = id,
        name = name,
        username = username,
        email = email,
        residence = homeDetails.toResidence()
    )

@JvmName("UserInfoTotoUserData")
fun List<UserInfo>.toUserData() = map(UserInfo::toUserData)

private fun HomeDetails.toResidence(): Residence {
    return Residence(street = street, city = city, state = state, zipcode = zipcode)
}
