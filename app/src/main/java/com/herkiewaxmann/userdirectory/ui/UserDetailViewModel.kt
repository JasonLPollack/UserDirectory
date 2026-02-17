package com.herkiewaxmann.userdirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.domain.DataStatus
import com.herkiewaxmann.userdirectory.domain.UserDetail
import com.herkiewaxmann.userdirectory.domain.UserDetailTransformer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserDetailState(
    val loading:Boolean = true,
    val user: UserDetail? = null,
    val errorMessage: String? = null
)

class UserDetailViewModel(
    private val repo: UsersRepo
): ViewModel() {
    val state : StateFlow<UserDetailState>
        field = MutableStateFlow(UserDetailState())

    fun queryUserById(id: Int) {
        viewModelScope.launch {
            repo.getUserDetailById(id).collect { dataStatus ->
                when (dataStatus) {
                    is DataStatus.Loading -> {
                        state.value = state.value.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }

                    is DataStatus.Error -> {
                        Log.e(
                            "UserDetail",
                            dataStatus.e.toString()
                        )
                        state.value = state.value.copy(
                            loading = false,
                            errorMessage = "Error"
                        )
                    }

                    is DataStatus.Success -> {
                        val user = UserDetailTransformer.fromDummyJSONUser(dataStatus.data)
                        state.value = state.value.copy(
                            loading = false,
                            user = user,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }
}