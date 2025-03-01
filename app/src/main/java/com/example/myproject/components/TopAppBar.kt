package com.example.myproject.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Screen.Search.route -> "แนะนำลดหย่อนภาษี"
        Screen.TaxAdd.route -> "เพิ่มภาษี"
        Screen.Notification.route -> "การแจ้งเตือน"
        Screen.Profile.route -> "โปรไฟล์"
        else -> "แอปของฉัน" // ค่าเริ่มต้น
    }

    CenterAlignedTopAppBar(
        title = {
            // ใช้ Box เพื่อจัดวาง Text ให้อยู่ตรงกลางทั้งแนวนอนและแนวตั้ง
            Box(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        },
        modifier = modifier.height(100.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF00D09E)
        )
    )
}