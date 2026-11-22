package icsd19218.messagingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class UserMessage {
    enum class Origin {
        Received,
        Sent
    }
    val messageOrigin: Origin
    val message: String

    constructor(messageOrigin: Origin, message: String) {
        this.messageOrigin = messageOrigin
        this.message = message
    }
}

@Composable
fun ChatScreen(username: String, avatarUrl: String? = null) {
    // Retrieving previous messages
    val previousMessages = remember { mutableStateListOf<UserMessage>() }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    UserDisplay(
                        username = username,
                        avatarUrl = avatarUrl
                    )
                },
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                MessageInputWithSend(previousMessages = previousMessages)
            }
        }
    ) { innerPadding ->
        ChatHistory(
            modifier = Modifier.padding(paddingValues = innerPadding),
            previousMessages = previousMessages
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(username = "icsd19218")
}

@Composable
fun ChatHistory(
    modifier: Modifier = Modifier,
    previousMessages: SnapshotStateList<UserMessage>
) {
    // Used to make list scroll to the bottom
    val listState = rememberLazyListState()

    // Runs the following block every time size of list changes
    LaunchedEffect(previousMessages.size) {
        if (previousMessages.isNotEmpty())
            listState.scrollToItem(index = previousMessages.size - 1)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        items(previousMessages) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = it.message,
                    textAlign = if (it.messageOrigin == UserMessage.Origin.Sent) TextAlign.End else TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Clip,
                    maxLines = 4
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatHistoryPreview() {
    ChatHistory(
        previousMessages = remember {
            mutableStateListOf(
                UserMessage(messageOrigin = UserMessage.Origin.Sent, message = "Is anyone there?"),
                UserMessage(messageOrigin = UserMessage.Origin.Received, message = "Hello there"),
                UserMessage(messageOrigin = UserMessage.Origin.Sent, message = "Confirmed?"),
                UserMessage(messageOrigin = UserMessage.Origin.Received, message = "Confirmed!")
            )
        }
    )
}

@Composable
fun MessageInputWithSend(previousMessages: SnapshotStateList<UserMessage>) {
    var messageField by remember { mutableStateOf(value = "") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Message field
        TextField(
            modifier = Modifier.weight(1f), // Fill the remaining space with this element
            value = messageField,
            onValueChange = { messageField = it },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = "Message...",
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
            },
            shape = CircleShape,
            maxLines = 4
        )

        // Submit button
        Button(
            modifier = Modifier.padding(horizontal = 8.dp),
            shape = CircleShape,
            onClick = {
                // Sending message to history if it's valid
                if (messageField.isNotEmpty()) {
                    previousMessages.add(
                        UserMessage(
                            messageOrigin = UserMessage.Origin.Sent,
                            message = messageField
                        )
                    )

                    // Clearing message field
                    messageField = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Submit message"
            )
        }
    }
}