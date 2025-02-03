package com.example.myproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myproject.screen.NavGraph
import com.example.myproject.screen.Screen
import com.example.myproject.ui.theme.MyProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyProjectTheme {
                MyScaffoldLayout()
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProjectTheme {
        Greeting("Android")
    }
}

@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context) {
    val navigationItems = listOf(
        Screen.Home,
        Screen.Search,
        Screen.TaxAdd,
        Screen.Notification,
        Screen.Profile
    )
    var selectedScreen by remember { mutableIntStateOf(0) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
        }
        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(100.dp)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 0.dp, bottomEnd = 0.dp)),
            containerColor = Color(0xFFDFF7E2)
        )  {
            navigationItems.forEachIndexed { index, screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = { Text(text = screen.name) },
                    selected = (selectedScreen == index),
                    onClick = {
                        selectedScreen = index
                        navController.navigate(screen.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color(0xFF4CAF50),
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color(0xFFE8F5E9)
                    )
                )
                    }

            }
        }
    }

@Composable
fun MyScaffoldLayout(){
    val contextForToast = LocalContext.current.applicationContext
    val navController = rememberNavController()
    Scaffold (
        bottomBar = { MyBottomBar(navController, contextForToast)}
    ){
            paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){ }
        NavGraph(navController = navController)
    }
}