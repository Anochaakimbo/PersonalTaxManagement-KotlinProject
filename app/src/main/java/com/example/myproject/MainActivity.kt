package com.example.myproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myproject.components.MyBottomBar
import com.example.myproject.navigation.NavGraph
import com.example.myproject.ui.theme.MyProjectTheme
import com.example.myproject.loginandsignup.SharedPreferencesManager
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyProjectTheme {
                MyScreen()
            }
        }
    }
}



@Composable
fun MyScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = remember { SharedPreferencesManager(context) }

    var isLoggedIn by remember { mutableStateOf(sharedPreferences.isLoggedIn) }

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                Log.d("DEBUG", "BottomBar แสดงผล")
                MyBottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            onLoginSuccess = {
                Log.d("DEBUG", "Login สำเร็จ, เปลี่ยน isLoggedIn เป็น true")
                isLoggedIn = true
            },
            onLogout = {
                Log.d("DEBUG", "Logout สำเร็จ, เปลี่ยน isLoggedIn เป็น false")
                isLoggedIn = false // ✅ บังคับอัปเดต UI
            }
        )
    }
}


