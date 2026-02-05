package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.herkiewaxmann.userdirectory.domain.User

@Composable
fun UserListEntry(
    user: User,
    onClicked: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClicked(user.id) }
    ) {
        Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun PreviewUserListEntry() {
    val user = User(1, "John Doe")
    UserListEntry(user, onClicked = {} )
}