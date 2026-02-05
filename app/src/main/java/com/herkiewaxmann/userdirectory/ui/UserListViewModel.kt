package com.herkiewaxmann.userdirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herkiewaxmann.userdirectory.data.DataStatus
import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.domain.User
import com.herkiewaxmann.userdirectory.domain.UserTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class UserListState(
    val loading:Boolean = true,
    val users: List<User> = emptyList()
)

class UserListViewModel: ViewModel() {
    private val _state = MutableStateFlow(UserListState())
    val state = _state.asStateFlow()

    val repo = UsersRepo()

    init {
        queryAllUsers()
    }

    fun getUserById(id: Int): User? = _state.value.users.firstOrNull { it.id == id }

    fun queryAllUsers() {
        viewModelScope.launch {
            repo.getUsers().collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }
                    is DataStatus.Error -> {
                        Log.e("UserList", dataStatus.exception.localizedMessage)
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
                        Log.e("UserList", dataStatus.exception.localizedMessage)
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