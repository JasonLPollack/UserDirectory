package com.herkiewaxmann.userdirectory.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.herkiewaxmann.userdirectory.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetailContent(
    id: Int,
    modifier: Modifier = Modifier
) {
    val userDetailViewModel: UserDetailViewModel = koinViewModel()
    val userState by userDetailViewModel.state.collectAsState()

    LaunchedEffect(id) {
        userDetailViewModel.queryUserById(id)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetail(
    id: Int,
    onBack: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.user_detail_header)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        UserDetailContent(
            id,
            Modifier.padding(innerPadding)

        )
    }
}