package dev.abhishekagrahari.questionbank.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseLayout(
    navController: NavController,
    title: String = "Questions Bank",
    darkTheme: Boolean,  // Accept current theme state
    onThemeChange: (Boolean) -> Unit, // Callback to change theme
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // 🔥 Restored drawerState
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    CustomDrawerLayout(navController, coroutineScope, drawerState) { // 🔥 Pass drawerState
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
                        }
                    },
                    actions = {
                        // ⚙️ Settings Dropdown
                        Box {
                            IconButton(onClick = { expanded = true }) {
                                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                            }
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                DropdownMenuItem(
                                    text = { Text("Light Mode") },
                                    onClick = {
                                        onThemeChange(false) // Switch to Light Mode
                                        expanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Dark Mode") },
                                    onClick = {
                                        onThemeChange(true) // Switch to Dark Mode
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                )
            },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues),
                    color = if (darkTheme) Color.DarkGray else Color.White
                ) {
                    content()
                }
            }
        )
    }
}


@Composable
fun CustomDrawerLayout(
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    content: @Composable () -> Unit)
{
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, coroutineScope, drawerState)
        },
        gesturesEnabled = true,
        scrimColor = Color.Black.copy(alpha = 0.4f) // Darkens the background slightly
    ) {
        content()
    }
}

@Composable
fun DrawerContent(navController: NavController,coroutineScope: CoroutineScope , drawerState: DrawerState) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f) // **Set drawer width to 70%**
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 50.dp)
    ) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerItem(
            coroutineScope = coroutineScope,
            text = "Homepage",
            navController = navController,
            viewid = "home",
            drawerState = drawerState
        )
        DrawerItem(
            coroutineScope=coroutineScope ,
            text = "Contact Developer",
            navController = navController,
            viewid = "contact_us",
            drawerState = drawerState
        )
        DrawerItem(
            coroutineScope = coroutineScope ,
            text = "About",
            navController = navController,
            viewid = "about_us",
            drawerState = drawerState
        )
    }
    }
}
@Composable
fun DrawerItem(
    text: String,
    navController: NavController,
    coroutineScope: CoroutineScope,
    viewid: String,
    drawerState: DrawerState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(viewid)
                coroutineScope.launch { drawerState.close() } // Close drawer after navigation
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface, // Adapts to light/dark theme
            contentColor = MaterialTheme.colorScheme.onSurface // Ensures contrast
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface // Ensures text visibility
            )
        )
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant) // Subtle divider color
}
