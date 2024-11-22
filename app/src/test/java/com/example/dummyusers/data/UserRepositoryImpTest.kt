package com.example.dummyusers.data

import com.example.dummyusers.core.UserLocalDatasourceFake
import com.example.dummyusers.core.UserRemoteDataSourceFake
import com.example.dummyusers.data.local.Residence
import com.example.dummyusers.data.local.UsersData
import com.example.dummyusers.data.remote.HomeDetails
import com.example.dummyusers.data.remote.UserInfo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImpTest {

    private val homeDetails1 =
        HomeDetails(street = "123 Main St", city = "San Francisco", state = "CA", zipcode = "94111")
    private val userInfo1 = UserInfo(
        id = 1,
        name = "John Doe",
        username = "johndoe",
        email = "johndoe@example.com",
        homeDetails = homeDetails1
    )

    private val homeDetails2 =
        HomeDetails(street = "456 Market St", city = "Los Angeles", state = "CA", zipcode = "90001")
    private val userInfo2 = UserInfo(
        id = 2,
        name = "Jane Doe",
        username = "janedoe",
        email = "janedoe@example.com",
        homeDetails = homeDetails2
    )

    private val residence1 =
        Residence(street = "789 Oak St", city = "New York", state = "NY", zipcode = "10001")
    private val usersData1 = UsersData(
        id = 1,
        name = "Jim Smith",
        username = "jimsmith",
        email = "jimsmith@example.com",
        residence = residence1
    )

    private val residence2 =
        Residence(street = "123 Main St", city = "Chicago", state = "IL", zipcode = "60601")
    private val userData2 = UsersData(
        id = 2,
        name = "Sara Wilson",
        username = "sarawilson",
        email = "sarawilson@example.com",
        residence = residence2
    )

    private val latestHomeDetails =
        HomeDetails(street = "456 Holf St", city = "Texas", state = "TX", zipcode = "40501")
    private val newUserInfo = UserInfo(
        id = 10,
        name = "Cyber corp ",
        username = "cybercorp",
        email = "cybercorp@example.com",
        homeDetails = latestHomeDetails
    )
    private val allNewUserInfo = listOf(newUserInfo)

    private val remoteUsers = listOf(userInfo1, userInfo2)
    private val localUsers = listOf(usersData1, userData2)

    // Test dependencies
    private lateinit var userRemoteDataSource: UserRemoteDataSourceFake
    private lateinit var userLocalDatasource: UserLocalDatasourceFake

    private var testDispatcher = UnconfinedTestDispatcher()
    private lateinit var testScope: TestScope

    // Class under test
    private lateinit var userRepository: UserRepositoryImp

    @Before
    fun createRepository() {
        userRemoteDataSource = UserRemoteDataSourceFake(remoteUsers.toMutableList())
        userLocalDatasource = UserLocalDatasourceFake(localUsers)
        testScope = TestScope(testDispatcher)

        // Get a reference to the class under test
        userRepository = UserRepositoryImp(
            remoteDataSource = userRemoteDataSource,
            localDataSource = userLocalDatasource,
            dispatcher = testDispatcher,
        )
    }

    @Test
    fun obtainAllUsersProfile_RepositoryHasEmptyLocalAndRemoteDatasource() = testScope.runTest {
        // given An Empty Local And Remote Datasource
        userRemoteDataSource.userInfo?.clear()
        userLocalDatasource.deleteUserData()

        // When obtainAllUsersProfile is triggered
        val userProfiles = userRepository.obtainAllUsersProfile(deleteExistingRecord = true)

        // Then the total users is zero
        assertThat(userProfiles.size).isEqualTo(0)
    }

    @Test
    fun obtainAllUsersProfile_fetchFromServerAndNotDeleteExistingData() = testScope.runTest {

        // When obtainAllUsersProfile is triggered
        val userProfiles = userRepository.obtainAllUsersProfile(makeNetworkCallFirst = true)

        // Assert That the total users are 2
        assertThat(userProfiles.size).isEqualTo(2)
    }

    @Test
    fun obtainAllUsersProfile_obtainOnlyDataAlreadyStored() = testScope.runTest {

        // Given data already in the database
        val users = localUsers

        // When obtainAllUsersProfile is triggered
        val userProfiles = userRepository.obtainAllUsersProfile()

        // Then assert that the total users is zero
        assertThat(userProfiles[0].name).isEqualTo(users[0].name)
    }

    @Test
    fun obtainAllUsersProfile_repositoryStoresAfterGettingUserFromServer() = testScope.runTest {
        // Trigger the repository to fetch users from the server and delete existing users
        val first = userRepository.obtainAllUsersProfile(
            makeNetworkCallFirst = true, deleteExistingRecord = true
        )

        // Supply new data to the remote data source
        userRemoteDataSource.userInfo = allNewUserInfo.toMutableList()

        // Load the users again without forcing an update and deleting existing records
        val second = userRepository.obtainAllUsersProfile(false)

        // Then assert that the first users emails are the same
        assertThat(second[0].name).isEqualTo(first[0].name)
    }

    @Test
    fun obtainAllUsersProfile_requestsAllUserFromRemoteDataSource() = testScope.runTest {

        // given the data returned by the server
        val allUsers = remoteUsers

        // When Users are requested from the UserRepository
        val users = userRepository.obtainAllUsersProfile(
            makeNetworkCallFirst = true, deleteExistingRecord = true
        )

        // Then assert that the 1st emails are the same
        assertThat(users[0].email).isEqualTo(allUsers[0].email)
    }

    @Test(expected = Exception::class)
    fun obtainAllUsersProfile_prePopulatedLocalDatasourceRemoteUnavailable() = testScope.runTest {
        // Given an no data from server
        userRemoteDataSource.userInfo = null

        // When users are requested fom the server
        userRepository.obtainAllUsersProfile(true)

        // Then throw an exception even with a populated db
    }

    @Test
    fun obtainAllUsersProfile_notEmptyLocalDatasourceRemoteUnavailable() = testScope.runTest {
        // Given an no data from server
        userRemoteDataSource.userInfo = null

        // When users are not requested from the server the userRepo fetches from the localDataSrc
        val datsFormLocalDataSource = userRepository.obtainAllUsersProfile()

        // Then confirm the username of the first item stored and retrieved are the same
        assertThat(datsFormLocalDataSource[0].username).isEqualTo(localUsers[0].username)
    }

    @Test(expected = Exception::class)
    fun obtainAllUsersProfile_WithBothDataSourcesUnavailable_throwsError() = testScope.runTest {
        // Given that both datasource are unavailable
        userRemoteDataSource.userInfo = null
        userLocalDatasource.userData = null

        // When the userRepository is triggered to get all data
        userRepository.obtainAllUsersProfile()

        // Then throw an exception
    }

    @Test
    fun getUserProfileById_fetchFromServerAndNotDeleteExistingData() = testScope.runTest {

        // Given a user,userId and instantiating the DB
        val userData = mutableListOf(usersData1)
        val userId = userInfo1.id
        userLocalDatasource = UserLocalDatasourceFake(userData)

        // Obtain a user from the local data source
        val firstResult = userRepository.getUserProfileById(userId)

        // Change the user on the remoteDataSource
        userRemoteDataSource.userInfo = allNewUserInfo.toMutableList()

        // When a user with obtained with the same id
        val secondResult = userRepository.getUserProfileById(userId)

        // Then the first and second users obtained should have the same username
        assertThat(secondResult?.username).isEqualTo(firstResult?.username)
    }

    @Test
    fun getUserProfileById_makeNetworkCallWithId() = testScope.runTest {
        // Given a user,userId and passing it into the remoteDataSource
        val userData = userInfo1
        val userId = userInfo1.id
        userRemoteDataSource.userInfo = mutableListOf(userData)

        // When network call is made to pull a specific user
        val result = userRepository.getUserProfileById(userId, makeNetworkCallFirst = true)

        // Confirm the id's are similar and the db and likewise the userName
        assertThat(result?.id).isEqualTo(userId)
        assertThat(userData.username).isEqualTo(result?.username)
    }

    @Test
    fun saveUserProfiles_addToExistingRecord() = testScope.runTest {

        // Given a new UserProfile
        val userProfile = allNewUserInfo.toUserProfile()

        // When a newUser is saved to the UserRepository
        userRepository.saveUserProfiles(userProfile)

        // Then Assert that it the only user is stored in the localDataSource
        val localUserData = userLocalDatasource.userData
        assertThat(localUserData?.size).isEqualTo(3)
        assertThat(localUserData?.firstOrNull { it.id == newUserInfo.id }).isNotNull()
    }

    @Test
    fun saveUserProfiles_deleteBeforeSavingToLocal() = testScope.runTest {

        // Given new UserProfile and that the record should be deleted
        val userProfile = allNewUserInfo.toUserProfile()
        val shouldDeleteRecord = true

        // When a user is saved to the usersRepository
        userRepository.saveUserProfiles(userProfile, shouldDeleteRecord)

        // Then confirm that only one user is stored in the localDataSrc and it is the one inserted
        val localUserData = userLocalDatasource.userData
        assertThat(localUserData?.size).isEqualTo(1)
        assertThat(localUserData?.firstOrNull { it.id == newUserInfo.id }).isNotNull()
    }

    @Test
    fun deleteAllUsers_checkContentOfDb_DeleteAndCheck() = testScope.runTest {
        // When all users are fetched
        val initialUsers = userRepository.obtainAllUsersProfile()

        // Then verify the size is 2 which is the default size of data inserted
        assertThat(initialUsers.size).isEqualTo(2)

        // When all users are deleted
        userRepository.deleteAllUsers()

        // Then verify that no user exist in the localDataSource
        val usersAfterClearingDb = userRepository.obtainAllUsersProfile()
        assertThat(usersAfterClearingDb).isEmpty()
    }
}
