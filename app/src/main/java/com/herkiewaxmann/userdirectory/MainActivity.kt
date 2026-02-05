package com.herkiewaxmann.userdirectory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.herkiewaxmann.userdirectory.domain.InvalidUser
import com.herkiewaxmann.userdirectory.ui.UserDetail
import com.herkiewaxmann.userdirectory.ui.UserList
import com.herkiewaxmann.userdirectory.ui.UserListViewModel
import com.herkiewaxmann.userdirectory.ui.theme.UserDirectoryTheme

data object UserListScene
data class UserDetailScene(val id: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userListViewModel: UserListViewModel = viewModel()
            val backStack = remember { mutableStateListOf<Any>(UserListScene) }
            UserDirectoryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    )
                    {
                        NavDisplay(
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            entryProvider = { key ->
                                when (key) {
                                    is UserListScene -> NavEntry(key) {
                                        UserList(
                                            userListViewModel = userListViewModel,
                                            onDetailClicked = { id ->
                                                backStack.add(UserDetailScene(id))
                                            }
                                        )
                                    }

                                    is UserDetailScene -> NavEntry(key) {
                                        val user = userListViewModel.getUserById(key.id) ?: InvalidUser
                                        UserDetail(
                                            user,
                                            onBack = { backStack.removeLastOrNull() }
                                        )
                                    }

                                    else -> NavEntry(Unit) {
                                        Text("Unknown route")
                                    }
                                }
                            }
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserDirectoryTheme {
        Greeting("Android")
    }
}