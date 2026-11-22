package icsd19218.messagingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserDisplay(
    username: String,
    avatarUrl: String? = null,
    lastMessage: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Image(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .weight(0.25f),
            painter = painterResource(R.drawable.avatar_default),
            contentDescription = "Avatar of $username",
            contentScale = ContentScale.Crop,
        )

        // Pair for username and last message
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.75f)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Username
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = username,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )

            // Last message
            if (lastMessage != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = lastMessage,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDisplayPreview() {
    UserDisplay(username = "icsd19218", lastMessage = "Hello world!")
}