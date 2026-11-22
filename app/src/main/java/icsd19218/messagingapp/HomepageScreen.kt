package icsd19218.messagingapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomepageScreen(
    onUserSelected: (String, String?) -> Unit,
    onUserAddSelected: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Messaging App",
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    maxLines = 1
                )
            })
        },
        floatingActionButton = { AddUserButton( onClick = { onUserAddSelected() } ) },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        UsersList(
            Modifier.padding(paddingValues = innerPadding),
            usersInfo = arrayOf(
                Triple(
                    "icsd19218",
                    null,
                    "Hello dear world!"
                ),
                Triple(
                    "Long message example",
                    null,
                    "Salutations your grace. We have a serious situation that requires your attention"
                ),
                Triple(
                    "No message",
                    null,
                    ""
                )
            ),
            onUserSelected = onUserSelected
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomepageScreenPreview() {
    HomepageScreen(onUserSelected = { username, avatarUrl -> {}}, onUserAddSelected = {})
}

@Composable
fun UsersList(
    modifier: Modifier = Modifier,
    usersInfo: Array<Triple<String, String?, String>>,
    onUserSelected: (String, String?) -> Unit) {
    // Using Lazy Column to Render Only Visible Items
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(usersInfo) {
            Button(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = { onUserSelected(it.first, it.second) }
            ) {
                UserDisplay(
                    username = it.first,
                    avatarUrl = it.second,
                    lastMessage = it.third
                )
            }
        }
    }
}

@Composable
fun AddUserButton(text: String = "+", onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip
        )
    }
}