package com.herkiewaxmann.userdirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herkiewaxmann.userdirectory.R
import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.domain.DataStatus
import com.herkiewaxmann.userdirectory.domain.User
import com.herkiewaxmann.userdirectory.domain.UserTransformer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class UserListState(
    val loading:Boolean = true,
    val users: List<User> = emptyList(),
    val errorMessage: UiText? = null
)

class UserListViewModel(
    private val repo: UsersRepo
): ViewModel() {
    val state : StateFlow<UserListState>
        field = MutableStateFlow(UserListState())

    init {
        queryAllUsers()
    }

    fun queryAllUsers() {
        viewModelScope.launch {
            repo.getUsers().collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        state.value = state.value.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }
                    is DataStatus.Error -> {
                        Log.e(
                            "UserList",
                            dataStatus.e.toString()
                        )
                        state.value = state.value.copy(
                            loading = false,
                            errorMessage = UiText.StringResource(R.string.error_fetching)
                        )
                    }
                    is DataStatus.Success -> {
                        val userList = dataStatus.data.users.map {
                            UserTransformer.fromDummyJSONUser(it)
                        }
                        state.value = state.value.copy(
                            loading = false,
                            users = userList,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun queryUsersByName(name: String) {
        viewModelScope.launch {
            repo.getUsersByName(name).collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        state.value = state.value.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }
                    is DataStatus.Error -> {
                        Log.e(
                            "UserList",
                            dataStatus.e.toString()
                        )
                        println("Uh oh! Error loading users")
                        state.value = state.value.copy(
                            loading = false,
                            errorMessage = UiText.StringResource(
                                R.string.error_fetching,
                                arrayOf(name)
                            )
                        )
                    }
                    is DataStatus.Success -> {
                        val userList = dataStatus.data.users.map {
                            UserTransformer.fromDummyJSONUser(it)
                        }
                        state.value = state.value.copy(
                            loading = false,
                            users = userList,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }
}