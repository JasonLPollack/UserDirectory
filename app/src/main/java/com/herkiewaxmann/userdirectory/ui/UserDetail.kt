package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetail(
    id: Int,
    onBack: () -> Unit
) {
    val userDetailViewModel: UserDetailViewModel = koinViewModel()
    val userState by userDetailViewModel.state.collectAsState()

    LaunchedEffect(id) {
        userDetailViewModel.queryUserById(id)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (userState.loading) {
            CircularProgressIndicator()
        } else {
            userState.user?.let { user ->
                AsyncImage(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    model = user.imageUrl,
                    contentDescription = "Image for ${user.name}"
                )
                Text(user.name)
                if (user.phone != null) {
                    Text("Phone: ${user.phone}")
                }
                if (user.email != null) {
                    Text("Email: ${user.email}")
                }
            }
        }
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}