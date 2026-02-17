package com.herkiewaxmann.userdirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herkiewaxmann.userdirectory.data.DataStatus
import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.domain.User
import com.herkiewaxmann.userdirectory.domain.UserTransformer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class UserListState(
    val loading:Boolean = true,
    val users: List<User> = emptyList()
)

class UserListViewModel(
    private val repo: UsersRepo
): ViewModel() {
    val _state = MutableStateFlow(UserListState())

    // Use the .onStart() method to initialize data rather than in the init block.
    // Would prefer to use kotlin explicit backing fields, but currently this is a bit messy.
    val state = _state
        .onStart { queryAllUsers() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UserListState()
        )

    fun queryAllUsers() {
        viewModelScope.launch {
            repo.getUsers().collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }
                    is DataStatus.Error -> {
                        Log.e(
                            "UserList",
                            dataStatus.exception.localizedMessage.orEmpty()
                        )
                        _state.value = _state.value.copy(loading = false)
                    }
                    is DataStatus.Success -> {
                        val userList = dataStatus.data.users.map {
                            UserTransformer.fromDummyJSONUser(it)
                        }
                        _state.value = _state.value.copy(
                            loading = false,
                            users = userList
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
                        _state.value = _state.value.copy(loading = true)
                    }
                    is DataStatus.Error -> {
                        Log.e(
                            "UserList",
                            dataStatus.exception.localizedMessage.orEmpty()
                        )
                        println("Uh oh! Error loading users")
                        _state.value = _state.value.copy(loading = false)
                    }
                    is DataStatus.Success -> {
                        val userList = dataStatus.data.users.map {
                            UserTransformer.fromDummyJSONUser(it)
                        }
                        _state.value = _state.value.copy(
                            loading = false,
                            users = userList
                        )
                    }
                }
            }
        }
    }
}