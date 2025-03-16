package dev.abhishekagrahari.questionbank.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
    navController: NavController ,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    title: String = "Let's Create Questions Easily",
    content: @Composable () -> Unit,
) {
    val primaryColor = Color(0xFF6200EA) // Custom Primary Color
    val onPrimaryColor = Color.White    // Custom On Primary Color
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        modifier = Modifier
            .background(Color(0xFF8EC5E3))
            .fillMaxSize(),
        drawerContent = {
            if(drawerState.isOpen){
                Spacer(modifier= Modifier.height(32.dp).background(Color(0xFF143854)))
                DrawerContent(navController = navController, coroutineScope = coroutineScope, drawerState = drawerState)
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 22.sp,
                                color = onPrimaryColor
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu Icon",
                                tint = onPrimaryColor
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary                   )
                )
            },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues),
                    color = Color(0xFF8EC5E3)
                ) {
                    content()
                }
            }
        )
    }
}

@Composable
fun DrawerContent(navController: NavController,coroutineScope: CoroutineScope , drawerState: DrawerState) {
    Column(modifier = Modifier.padding(top =50.dp).background(MaterialTheme.colorScheme.primary).fillMaxSize()) {
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

@Composable
fun DrawerItem(text: String, coroutineScope: CoroutineScope , navController: NavController, viewid: String, drawerState: DrawerState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(viewid)
                // Close the drawer after clicking on an item
                coroutineScope.launch {
                    drawerState.close()
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth().padding(8.dp)
                .padding(8.dp),
            style = TextStyle(fontSize = 18.sp)
        )
    }
    HorizontalDivider()
}