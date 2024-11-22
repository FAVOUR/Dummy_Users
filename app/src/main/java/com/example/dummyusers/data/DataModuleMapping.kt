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

@JvmName("userDataToUserProfile")
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

@JvmName("userProfileToUserData")
fun List<UserProfile>.toUserData() = map(UserProfile::toUserData)

private fun UserLocation.toResidence(): Residence {
    return Residence(street = street, city = city, state = state, zipcode = zipcode)
}

fun UsersData.toLocalData() = LocalUser(
    id = id,
    name = name,
    username = username,
    email = email,
    userLocationData = residence.toUserLocationData()
)

@JvmName("userDataToLocalData")
fun List<UsersData>.toLocalData() = map(UsersData::toLocalData)

private fun Residence.toUserLocationData(): UserLocationData {
    return UserLocationData(street = street, city = city, state = state, zipcode = zipcode)
}

private fun UserInfo.toUserData() =
    UsersData(
        id = id,
        name = name,
        username = username,
        email = email,
        residence = homeDetails.toResidence()
    )

@JvmName("userInfoTotoUserData")
fun List<UserInfo>.toUserData() = map(UserInfo::toUserData)

private fun HomeDetails.toResidence(): Residence {
    return Residence(street = street, city = city, state = state, zipcode = zipcode)
}


fun UserInfo.toUserProfile() =
    UserProfile(
        id = id,
        name = name,
        username = username,
        email = email,
        userLocation = homeDetails.toUserLocation()
    )


@JvmName("userInfoToUserProfile")
fun List<UserInfo>.toUserProfile() = map(UserInfo::toUserProfile)


private fun HomeDetails.toUserLocation(): UserLocation {
    return UserLocation(street = street, city = city, state = state, zipcode = zipcode)
}
