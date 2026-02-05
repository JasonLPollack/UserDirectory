package com.herkiewaxmann.userdirectory.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.herkiewaxmann.userdirectory.domain.User

@Composable
fun UserListEntry(
    user: User,
    onClicked: (id: Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClicked(user.id) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp, 32.dp),
                model = user.imageUrl,
                contentDescription = "User Image for ${user.name}"
            )
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}

@Preview
@Composable
fun PreviewUserListEntry() {
    val user = User(1, "John Doe")
    UserListEntry(user, onClicked = {} )
}