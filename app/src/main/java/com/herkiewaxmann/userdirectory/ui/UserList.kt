package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchBar(
    onSearch: (String)->Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics{ isTraversalGroup = true }
    )
    {
        val textFieldState = rememberTextFieldState()
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it)}},
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search Users...") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // Display search results in a scrollable column
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
@Composable
fun UserList(
    userListViewModel: UserListViewModel,
    onDetailClicked: (Int) -> Unit
) {
    val userListState by userListViewModel.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        UserSearchBar(
            onSearch = { query ->
                if (query.isEmpty()) {
                    userListViewModel.queryAllUsers()
                } else {
                    userListViewModel.queryUsersByName(query)
                }
            },
            searchResults = emptyList()
        )

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
}