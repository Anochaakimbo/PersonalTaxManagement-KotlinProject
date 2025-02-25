package com.example.myproject.profilesubscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrivacyAndPermissionsScreen(navController: NavController) {
    var isChecked by remember { mutableStateOf(false) } // สถานะของ checkbox

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ข้อกำหนดและนโยบายความเป็นส่วนตัว",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "เมื่อคุณใช้แอปนี้ คุณยินยอมให้เราเข้าถึงข้อมูลบางอย่างที่จำเป็นต่อการให้บริการต่างๆ ซึ่งรวมถึงสิทธิ์การเข้าถึงกล้อง, ตำแหน่งที่ตั้ง, และไฟล์ภายในอุปกรณ์ของคุณ ...\n\n" +
                    "โดยการใช้แอปนี้คุณตกลงที่จะปฏิบัติตามข้อกำหนดที่ได้กล่าวถึงในนโยบายความเป็นส่วนตัว รวมถึงการจัดการข้อมูลส่วนบุคคลของคุณตามที่ได้ระบุไว้ในนโยบายนี้...\n\n" +
                    "หากคุณไม่ยอมรับข้อกำหนดเหล่านี้ คุณไม่สามารถใช้บริการได้..."
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox ให้ผู้ใช้ยินยอม
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "ยินยอมตามข้อกำหนดและนโยบายความเป็นส่วนตัว")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มยืนยันจะกดได้เมื่อ checkbox ถูกเลือก
        Button(
            onClick = {
                if (isChecked) {
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isChecked // ทำให้ปุ่มกดยืนยันได้เมื่อ checkbox ถูกเลือก
        ) {
            Text(text = "ยืนยัน")
        }
    }
}
