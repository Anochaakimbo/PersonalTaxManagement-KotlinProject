package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.myproject.components.TopAppBar
import com.example.myproject.navigation.Screen

@Composable
fun NotificationScreen(navController: NavController) {
    val contextForToast = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopAppBar(navController = navController, modifier = Modifier.zIndex(-1f)) // ✅ เพิ่ม TopAppBar
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Notification Screen")
            Button(
                onClick = {
                    Toast.makeText(contextForToast, "Notification", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Click", fontSize = 25.sp)
            }
        }
    }
}
