package com.herkiewaxmann.userdirectory.ui

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
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUsers().collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        println("Loading Users")
                    }
                    is DataStatus.Error -> {
                        println("Uh oh! Error loading users")
                        _state.value = _state.value.copy(loading = false)
                    }
                    is DataStatus.Success -> {
                        println("Success! Got ${dataStatus.data.users.size} users")
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

    fun getUserById(id: Int): User? = _state.value.users.firstOrNull { it.id == id }

}