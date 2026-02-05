package com.herkiewaxmann.userdirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herkiewaxmann.userdirectory.data.DataStatus
import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.domain.User
import com.herkiewaxmann.userdirectory.domain.UserDetail
import com.herkiewaxmann.userdirectory.domain.UserDetailTransformer
import com.herkiewaxmann.userdirectory.domain.UserTransformer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserDetailState(
    val loading:Boolean = true,
    val user: UserDetail? = null
)

class UserDetailViewModel: ViewModel() {
    private val _state = MutableStateFlow(UserDetailState())
    val state = _state.asStateFlow()

    val repo = UsersRepo()

    fun queryUserById(id: Int) {
        viewModelScope.launch {
            repo.getUserDetailById(id).collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is DataStatus.Error -> {
                        Log.e("UserDetail", dataStatus.exception.localizedMessage)
                        _state.value = _state.value.copy(loading = false)
                    }

                    is DataStatus.Success -> {
                        val user = UserDetailTransformer.fromDummyJSONUser(dataStatus.data)
                        _state.value = _state.value.copy(
                            loading = false,
                            user = user
                        )
                    }
                }
            }
        }
    }
}