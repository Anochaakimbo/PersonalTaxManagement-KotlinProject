package com.example.myproject.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myproject.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, modifier: Modifier = Modifier) {
    // ดึงข้อมูลหน้าปัจจุบันจาก NavController
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // กำหนด title ตาม route ปัจจุบัน
    val title = when (currentRoute) {
        Screen.Home.route -> "หน้าแรก"
        Screen.Search.route -> "ค้นหา"
        Screen.TaxAdd.route -> "เพิ่มภาษี"
        Screen.Notification.route -> "การแจ้งเตือน"
        Screen.Profile.route -> "โปรไฟล์"
        else -> "แอปของฉัน" // ค่าเริ่มต้น
    }

    // ✅ ใช้ zIndex(-1f) เพื่อให้มันอยู่ข้างหลังสุด
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(-1f) // ทำให้ TopBar อยู่ข้างหลัง
    ) {
        LargeTopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF00D09E)
            )
        )
    }
}
