package com.example.myproject.profilesubscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ContactUsScreen(navController: NavController) {
    var subject by remember { mutableStateOf(TextFieldValue("")) } // หัวข้อ
    var details by remember { mutableStateOf(TextFieldValue("")) } // รายละเอียด

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ติดต่อเรา",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ช่องกรอกหัวข้อ
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("หัวข้อ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // ช่องกรอกรายละเอียด
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("รายละเอียด") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มส่ง
        Button(
            onClick = {
                // ฟังก์ชั่นส่งข้อมูล เช่น ส่งไปยัง server หรือแสดงการยืนยัน
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ส่ง")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ปุ่มย้อนกลับ
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ย้อนกลับ")
        }
    }
}
