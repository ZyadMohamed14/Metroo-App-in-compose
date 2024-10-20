import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.nativemetrooapp.R
import com.example.nativemetrooapp.presentation.history.HistoryScreen
import com.example.nativemetrooapp.presentation.metroo.MetrooScreen
import com.example.nativemetrooapp.presentation.operation.ServicesScreen

import com.example.nativemetrooapp.ui.theme.primaryGreen


@Composable
fun DashBoardScreen(navController: NavController) {
    // State for the currently selected screen
    var currentScreen by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
    var context = LocalContext.current
    Scaffold(
        bottomBar = { BottomNavigationBar(currentScreen,) { currentScreen = it } },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                when (currentScreen) {
                    BottomNavItem.Home -> MetrooScreen(navController)
                    BottomNavItem.Services -> ServicesScreen()
                    BottomNavItem.History -> HistoryScreen(navController)
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(
    selectedScreen: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    val selectedItemColor = Color.White
    val unselectedItemColor = Color(0x73000000) // Adjust as necessary
    val backgroundColor = primaryGreen // Change to your desired color

    // Material 3 NavigationBar
    NavigationBar(containerColor = backgroundColor) {
        // Iterate over all enum entries
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(id = item.title)) },
                label = { Text(stringResource(id = item.title)) }, // Resolve string resource here
                selected = item == selectedScreen,
                onClick = { onItemSelected(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedItemColor,
                    unselectedIconColor = unselectedItemColor,
                    selectedTextColor = selectedItemColor,
                    unselectedTextColor = unselectedItemColor
                )
            )
        }
    }
}

enum class BottomNavItem(@StringRes val title: Int, val icon: ImageVector) {
    Home(R.string.home, Icons.Filled.Home),
    Services(R.string.services, Icons.Filled.Train),
    History(R.string.history, Icons.Filled.History);
}








