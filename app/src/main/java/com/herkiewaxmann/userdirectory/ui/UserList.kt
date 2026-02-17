package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserList(
    userListViewModel: UserListViewModel,
    onDetailClicked: (Int) -> Unit
) {
    val userListState by userListViewModel.state.collectAsState()
    var queryTerm by remember { mutableStateOf("") }

    // Local function to do the search
    fun doSearch() {
        if (queryTerm.isEmpty()) {
            userListViewModel.queryAllUsers()
        } else {
            userListViewModel.queryUsersByName(queryTerm)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        UserSearchBar(
            onSearch = { query ->
                queryTerm = query
                doSearch()
            },
            searchResults = emptyList()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        if (userListState.loading) {
            CircularProgressIndicator()
        } else if (userListState.errorMessage != null) {
            ErrorDisplayWithRetry(
                errorMessage = userListState.errorMessage!!.asString()
            ) {
                doSearch()
            }
        } else if (userListState.users.isEmpty()) {
            NoUsersFound(queryTerm)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            ) {
                items(userListState.users) { user ->
                    UserListEntry(
                        user,
                        onClicked = { id -> onDetailClicked(id) }
                    )
                }
            }
        }
    }
}

