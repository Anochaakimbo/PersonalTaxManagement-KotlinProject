package com.example.savedocument



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SeeDocumentScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) } // State สำหรับแสดง Dialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0)) // สีพื้นหลังอ่อน
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ส่วนหัว
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // **ปุ่มย้อนกลับ**
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ย้อนกลับ")
            }

            Spacer(modifier = Modifier.width(8.dp)) // เว้นระยะห่างระหว่างปุ่มกับข้อความ

            // **ข้อความ "เก็บเอกสาร"**
            Text(
                text = "เก็บเอกสาร",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000),
                modifier = Modifier.weight(1f) // ให้ข้อความขยายเต็มที่ ดันไปทางซ้าย
            )

            // ปุ่มโปรไฟล์
            OutlinedButton(
                onClick = { /* เปิดหน้าข้อมูลผู้ใช้ */ },
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Text("2568")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Default.Person, contentDescription = "โปรไฟล์")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // กล่องแสดงเนื้อหาที่อัปโหลด
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("แสดงเนื้อหาของ ''")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ปุ่มด้านล่าง
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.popBackStack() }, // กลับไปที่หน้า DocumentScreen
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)), // สีเขียว
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("เสร็จสิ้น", color = Color.Black)
            }

            Button(
                onClick = { showDialog = true }, // เปิด Dialog เมื่อกดลบ
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red), // สีแดง
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("ลบ", color = Color.Black)
            }
        }

        // **Dialog แจ้งเตือนเมื่อกด "ลบ"**
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false }, // ปิด Dialog เมื่อคลิกด้านนอก
                title = { Text("ยืนยันการลบ") },
                text = { Text("ต้องการลบเอกสาร '' นี้หรือไม่?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false // ปิด Dialog
                            // TODO: ใส่ฟังก์ชันลบเอกสารที่นี่
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Yes", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showDialog = false } // ปิด Dialog
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}
