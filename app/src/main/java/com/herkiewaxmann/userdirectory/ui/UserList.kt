package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun UserList(
    userListViewModel: UserListViewModel,
    onDetailClicked: (Int) -> Unit
) {
    val userListState by userListViewModel.state.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (userListState.loading) {
            item {
                CircularProgressIndicator()
            }
        } else {
            items(userListState.users) { user ->
                UserListEntry(
                    user,
                    onClicked = { id -> onDetailClicked(id) }
                )
            }
        }
    }
}