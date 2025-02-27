package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current.applicationContext

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "SearchScreen",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Screen.Notification.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = { isDrawerOpen = !isDrawerOpen }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu Icon", tint = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF00D09E)
                    )
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Search Screen")
                        Button(
                            onClick = {
                                Toast.makeText(contextForToast, "Search", Toast.LENGTH_SHORT).show()
                            }) {
                            Text(text = "Click", fontSize = 25.sp)
                        }
                    }
                }
            }
        )

        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = androidx.compose.animation.expandHorizontally(),
            exit = androidx.compose.animation.shrinkHorizontally(),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp))
                    .padding(16.dp)
                    .padding(top = 50.dp)
            ) {
                Column {
                    NavigationDrawerItem(
                        label = { Text("Home") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home Icon", tint = Color.Black) },
                        selected = false,
                        onClick = {
                            isDrawerOpen = false
                            navController.navigate("home_screen")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Profile") },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile Icon", tint = Color.Black) },
                        selected = false,
                        onClick = {
                            isDrawerOpen = false
                            navController.navigate("profile_screen")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Logout") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = "Logout Icon",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        selected = false,
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}
