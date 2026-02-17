package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.herkiewaxmann.userdirectory.R
import com.herkiewaxmann.userdirectory.domain.UserDetail
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetailRow(
    label: String,
    value: String?
) {
    value?.let { value ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "$label:",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(0.3f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}

@Composable
fun UserDetailContent(
    user: UserDetail?,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            user?.let { user ->
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineLarge
                )
                AsyncImage(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(256.dp)
                        .height(256.dp),
                    model = user.imageUrl,
                    placeholder = painterResource(R.drawable.outline_account_circle_24),
                    contentDescription = "Image for ${user.name}"
                )
                UserDetailRow(
                    stringResource(R.string.detail_phone),
                    user.phone
                )
                UserDetailRow(
                    stringResource(R.string.detail_email),
                    user.email
                )
                UserDetailRow(
                    stringResource(R.string.detail_location),
                    user.location
                )
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
    val userDetailViewModel: UserDetailViewModel = koinViewModel()
    val userState by userDetailViewModel.state.collectAsState()

    LaunchedEffect(id) {
        userDetailViewModel.queryUserById(id)
    }

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
            userState.user,
            userState.loading,
            Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun UserDetailPreview() {
    UserDetailContent(
        user = UserDetail(
            id = 1,
            name = "John Doe",
            phone = "301-555-1212",
            email = "john.doe@nowhere.com"
        ),
        loading = false
    )
}

@Preview
@Composable
fun UserDetailRowPreview() {
    Column {
        UserDetailRow(
            "Phone" , // R.string.detail_phone,
            "301-555-1212"
        )
        UserDetailRow(
            "Email" , // R.string.detail_phone,
            "john.doe@nowhere.com"
        )

    }
}