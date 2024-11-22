package com.example.dummyusers.core

import com.example.dummyusers.data.local.UserLocalDataSource
import com.example.dummyusers.data.local.UsersData
import kotlinx.coroutines.flow.Flow


class UserLocalDatasourceFake(initialUserData: List<UsersData>? = emptyList()) :
    UserLocalDataSource {

    private var _userData: MutableMap<Int, UsersData>? = null

    var userData: List<UsersData>?
        get() = _userData?.values?.toList()
        set(newUserData) {
            _userData = newUserData?.associateBy { it.id }?.toMutableMap()
        }

    init {
        userData = initialUserData
    }

    override fun observeUsers(): Flow<List<UsersData>> {
        TODO("Not yet implemented")
    }

    override suspend fun obtainSpecificUser(id: Int): UsersData? {
        val user =  _userData?.get(id)
        return user
    }

    override suspend fun obtainUserData(): List<UsersData> {
        return userData ?: throw Exception("UserData null")
    }


    override suspend fun saveUserData(usersData: List<UsersData>) {
        _userData?.putAll(usersData.associateBy { it.id })
    }

    override suspend fun saveAUserData(usersData: UsersData) {
        usersData.apply {
            _userData?.put(id, this)
        }
    }

    override suspend fun deleteUserData() {
        _userData?.clear()
    }

}