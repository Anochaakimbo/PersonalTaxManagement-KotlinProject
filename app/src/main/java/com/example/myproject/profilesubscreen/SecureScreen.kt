package com.example.myproject.profilesubscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class SecureScreenViewModel {
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    fun changePassword(context: android.content.Context, navController: NavController) {
        if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
            Toast.makeText(context, "รหัสผ่านถูกเปลี่ยนแล้ว!", Toast.LENGTH_SHORT).show()
            navController.popBackStack() // กลับไปหน้าก่อนหน้า
        } else {
            Toast.makeText(context, "รหัสผ่านไม่ตรงกัน!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SecureScreen(navController: NavController, viewModel: SecureScreenViewModel = SecureScreenViewModel()) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF8E2)), // สีพื้นหลังเขียวอ่อน
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "เปลี่ยนรหัสผ่าน",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.oldPassword,
                onValueChange = { viewModel.oldPassword = it },
                label = { Text("รหัสผ่านเก่า") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = viewModel.newPassword,
                onValueChange = { viewModel.newPassword = it },
                label = { Text("รหัสผ่านใหม่") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.confirmPassword = it },
                label = { Text("ยืนยันรหัสผ่าน") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.changePassword(context, navController) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F5BA9) // สีน้ำเงินเข้มตามปุ่ม "ยืนยัน"
                    )
                ) {
                    Text("ยืนยัน", color = Color.White)
                }

                Button(
                    onClick = { navController.popBackStack() }, // กลับไปหน้าก่อนหน้า
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF868686) // สีเทาเข้มตามปุ่ม "ยกเลิก"
                    )
                ) {
                    Text("ยกเลิก", color = Color.White)
                }
            }
        }
    }
}
