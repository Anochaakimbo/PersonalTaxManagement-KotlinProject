package com.example.myproject.profilesubscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    var subject by remember { mutableStateOf(TextFieldValue("")) }
    var details by remember { mutableStateOf(TextFieldValue("")) }

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
                    text = "ติดต่อเรา",
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
                    ContactTextField("หัวข้อ", subject) { subject = it }
                    ContactTextField("รายละเอียด", details, isMultiline = true) { details = it }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ปุ่มส่งข้อมูล
                    Button(
                        onClick = {
                            // ฟังก์ชันส่งข้อมูล เช่น ส่งไปยัง server หรือแสดงการยืนยัน
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C09E)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("ส่ง")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ปุ่มย้อนกลับ
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("ย้อนกลับ", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactTextField(label: String, value: TextFieldValue, isMultiline: Boolean = false, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color(0xff757575)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(if (isMultiline) 150.dp else 56.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFE8F5F1),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color(0xFF00C09E)
        ),
        shape = RoundedCornerShape(16.dp),
        maxLines = if (isMultiline) 5 else 1
    )
}
