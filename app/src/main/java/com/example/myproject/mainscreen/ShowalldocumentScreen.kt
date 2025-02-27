package com.example.myproject.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.OutlinedButton
@Composable
fun DocumentScreen(navController: NavHostController) {
    val documentList = listOf("เอกสาร A", "เอกสาร B", "เอกสาร C", "เอกสาร D") // รายการเอกสาร

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0)) // สีพื้นหลัง
            .padding(16.dp)
    ) {
        // **Row สำหรับปุ่มย้อนกลับ + ข้อความหัวข้อ + ปุ่มโปรไฟล์และเพิ่มเอกสาร**
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

            // **Column สำหรับปุ่มโปรไฟล์ + ปุ่มเพิ่มเอกสาร**
            Column(
                horizontalAlignment = Alignment.End
            ) {
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

                Spacer(modifier = Modifier.height(8.dp)) // เว้นระยะห่างระหว่างปุ่ม

                // ปุ่มเพิ่มเอกสาร **เพิ่ม onClick เพื่อไป UploadDocumentScreen**
                Button(
                    onClick = { navController.navigate("savedocument_screen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)), // สีเขียว
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("เพิ่มเอกสาร", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // เพิ่มระยะห่าง

        LazyColumn {
            items(documentList) { namedocument ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "ชื่อเอกสาร: $namedocument",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // ใช้ Row เพื่อดันปุ่มไปด้านขวา
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    navController.navigate("Seedetaildocument_screen")
                                }
                            ) {
                                Text("ดูรายละเอียด", color = Color(0xFF008000))
                            }
                        }
                    }
                }
            }
        }
    }
}
