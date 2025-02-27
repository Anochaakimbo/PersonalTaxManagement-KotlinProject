package com.example.myproject.mainscreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.myproject.R

@Composable
fun UploadDocumentScreen(navController: NavHostController) {
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher สำหรับการเลือกไฟล์
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedFileUri = uri // เมื่อผู้ใช้เลือกไฟล์
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0)) // สีพื้นหลัง
            .padding(16.dp)
    ) {
        // **Header: ปุ่มย้อนกลับ + "เก็บเอกสาร" + ปุ่มโปรไฟล์**
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ย้อนกลับ")
            }

            Text(
                text = "เก็บเอกสาร",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000),
                modifier = Modifier.weight(1f)
            )

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

        Spacer(modifier = Modifier.height(32.dp))

        // **กรอบสำหรับเลือกไฟล์**
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.LightGray, RoundedCornerShape(10.dp)),
//                .clickable { launcher.launch("image/*") }, // เรียกใช้งานสำหรับเลือกไฟล์รูปภาพ
            contentAlignment = Alignment.Center
        ) {
            // ถ้ายังไม่ได้เลือกไฟล์
            if (selectedFileUri == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // วงกลมไอคอน "+"
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White, shape = CircleShape)
                            .clickable { launcher.launch("image/*") }, // เรียกใช้งานสำหรับเลือกไฟล์รูปภาพ
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontSize = 36.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("เลือกไฟล์", color = Color.Gray)
                }
            } else {
                // ถ้าผู้ใช้เลือกไฟล์แล้ว
                Text(text = "ไฟล์ถูกเลือกแล้ว", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // **ปุ่มด้านล่าง**
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { if (selectedFileUri != null) {
                    Toast.makeText(context, "บันทึกไฟล์เรียบร้อย!", Toast.LENGTH_SHORT).show()
                    navController.navigateUp() // กลับหน้าหลัก
                } else {
                    Toast.makeText(context, "กรุณาเลือกไฟล์ก่อน", Toast.LENGTH_SHORT).show()
                } },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)), // สีเขียว
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("บันทึก", color = Color.Black)
            }

            Button(
                onClick = { navController.popBackStack() }, // ย้อนกลับ
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)), // สีเขียว
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("ยกเลิก", color = Color.Black)
            }
        }
    }
}
