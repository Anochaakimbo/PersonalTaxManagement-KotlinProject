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
    var isLoggedIn by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // ✅ ใช้ `rememberUpdatedState` เพื่อให้ UI อัปเดตทันที
    val sharedPreferences = remember { SharedPreferencesManager(context) }

    // ✅ โหลดค่าการล็อกอินทุกครั้งที่รีเฟรช UI
    LaunchedEffect(Unit) {
        isLoggedIn = sharedPreferences.isLoggedIn
    }

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                MyBottomBar(navController) // ✅ แสดงเฉพาะเมื่อเข้าสู่ระบบแล้ว
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            onLoginSuccess = {
                isLoggedIn = true // ✅ อัปเดตสถานะเมื่อเข้าสู่ระบบ
            }
        )
    }
}

