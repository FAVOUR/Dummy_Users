package com.example.dummyusers.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyusers.R
import com.example.dummyusers.domain.UserProfile
import com.example.dummyusers.domain.UserRepository
import com.example.dummyusers.ui.util.RequestState
import com.example.dummyusers.ui.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UsersListState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val userProfile: List<UserProfileData> = mutableListOf()
)

data class UserProfileData(
    val userId: Int = -1,
    val name: String = "",
    val userName: String = "",
)

/**
 * ViewModel for displaying the list of users.
 */

@HiltViewModel
class UserViewmodel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            userRepository.obtainAllUsersProfile(true, false)
        }
    }

    val uiState: StateFlow<UsersListState> =
        userRepository.observeUsersProfile().map { RequestState.Success(it) }
            .catch<RequestState<List<UserProfile>>>
            { emit(RequestState.Error(R.string.an_error_occurred)) }
            .map { userRequestState -> produceUsersListUiState(userRequestState) }.stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = UsersListState(isLoading = true)
            )

    private fun produceUsersListUiState(allUsers: RequestState<List<UserProfile>>) =
        when (allUsers) {
            RequestState.Loading -> {
                UsersListState(isLoading = true, isEmpty = true)
            }

            is RequestState.Error -> {
                UsersListState(isEmpty = true, isLoading = false)
            }

            is RequestState.Success -> {
                val userprofile = allUsers.data.map {
                    UserProfileData(
                        userId = it.id, name = it.name, userName = it.username
                    )
                }

                UsersListState(
                    isEmpty = allUsers.data.isEmpty(), isLoading = false, userProfile = userprofile
                )
            }
        }
}
