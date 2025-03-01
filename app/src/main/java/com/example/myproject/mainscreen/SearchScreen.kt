package com.example.myproject.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.myproject.components.TopAppBar
import com.example.myproject.navigation.Screen

@Composable
fun SearchScreen(navController: NavController) {
    // เรียกหน้า TaxSavingScreen ทันทีเมื่อเปิดหน้า SearchScreen
    LaunchedEffect(Unit) {
        navController.navigate(Screen.TaxSaving.route)
    }

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
            Text(text = "Search Screen")
        }
    }
}
