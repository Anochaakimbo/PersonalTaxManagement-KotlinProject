package com.example.myproject.profilesubscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrivacyAndPermissionsScreen(navController: NavController) {
    var isChecked by remember { mutableStateOf(false) }

    // พื้นหลังสีเขียวด้านบน
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00C09E))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 40.dp)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ข้อกำหนดและนโยบายความเป็นส่วนตัว",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }

            // พื้นหลังสีขาวโค้งมน
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "เมื่อคุณใช้แอปนี้ คุณยินยอมให้เราเข้าถึงข้อมูลบางอย่างที่จำเป็นต่อการให้บริการต่างๆ ซึ่งรวมถึงสิทธิ์การเข้าถึงกล้อง, ตำแหน่งที่ตั้ง, และไฟล์ภายในอุปกรณ์ของคุณ ...\n\n" +
                                "โดยการใช้แอปนี้คุณตกลงที่จะปฏิบัติตามข้อกำหนดที่ได้กล่าวถึงในนโยบายความเป็นส่วนตัว รวมถึงการจัดการข้อมูลส่วนบุคคลของคุณตามที่ได้ระบุไว้ในนโยบายนี้...\n\n" +
                                "หากคุณไม่ยอมรับข้อกำหนดเหล่านี้ คุณไม่สามารถใช้บริการได้...",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Checkbox ให้ผู้ใช้ยินยอม
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00C09E))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "ยินยอมตามข้อกำหนดและนโยบายความเป็นส่วนตัว")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ปุ่มยืนยัน
                    Button(
                        onClick = {
                            if (isChecked) {
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C09E)),
                        shape = RoundedCornerShape(16.dp),
                        enabled = isChecked // ปุ่มกดได้เมื่อ Checkbox ถูกเลือก
                    ) {
                        Text("ยืนยัน")
                    }
                }
            }
        }
    }
}
