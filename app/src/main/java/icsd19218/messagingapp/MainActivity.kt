package icsd19218.messagingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import icsd19218.messagingapp.ui.theme.MessagingAppTheme
import kotlinx.serialization.Serializable

// Navigation destinations keys
sealed class ScreenKey: NavKey {
    @Serializable
    data object Home: ScreenKey()
    @Serializable
    data class Chat(val username: String, val avatarUrl: String?): ScreenKey()
    @Serializable
    data object UserAdd: ScreenKey()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack(ScreenKey.Home)
            MessagingAppTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<ScreenKey.Home> {
                            HomepageScreen(
                                onUserSelected = {
                                    username, avatarUrl -> backStack.add(
                                        ScreenKey.Chat(username, avatarUrl)
                                    )
                                },
                                onUserAddSelected = {
                                    backStack.add(
                                        ScreenKey.UserAdd
                                    )
                                }
                            )
                        }
                        entry<ScreenKey.UserAdd> {
                            UserAddScreen()
                        }
                        entry<ScreenKey.Chat> { key ->
                            ChatScreen(
                                username = key.username,
                                avatarUrl = key.avatarUrl
                            )
                        }
                    }
                )
            }
        }
    }
}