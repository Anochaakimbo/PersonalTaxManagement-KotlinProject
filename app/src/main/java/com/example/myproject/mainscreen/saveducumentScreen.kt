package com.example.myproject.mainscreen

import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myproject.navigation.Screen
import com.example.myproject.viewmodel.DocumentViewModel
import com.example.myproject.loginandsignup.SharedPreferencesManager

@Composable
fun UploadDocumentScreen(
    navController: NavHostController,
    viewModel: DocumentViewModel = viewModel(),
    selectedYear: Int
) {
    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    val userId = sharedPreferencesManager.userId ?: 0 // Get userId from SharedPreferences

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val isLoading by viewModel.isLoading.collectAsState()

    // เปิดแกลเลอรี
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0)) // สีพื้นหลัง
            .padding(16.dp)
    ) {
        // **Header**
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ย้อนกลับ")
            }

            Text(
                text = "เก็บเอกสาร",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000),
                modifier = Modifier.weight(1f)
            )

            OutlinedButton(onClick = { /* เปิดหน้าข้อมูลผู้ใช้ */ }, shape = RoundedCornerShape(12.dp)) {
                Text(selectedYear.toString()) // แสดงปีที่เลือก
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Default.Person, contentDescription = "โปรไฟล์")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Frame อัปโหลดรูป**
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (selectedImageUri != null) {
                    // แสดงรูปที่เลือก
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "ภาพที่เลือก",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // แสดงไอคอน + ถ้ายังไม่มีรูป
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 36.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("แตะเพื่ออัปโหลด", color = Color.DarkGray, fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **ปุ่มบันทึกและยกเลิก**
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    selectedImageUri?.let { uri ->
                        viewModel.uploadFile(context, userId, uri, selectedYear) // Use userId from SharedPreferences
                    }
                    // นำทางกลับไปยังหน้า Showalldocument พร้อมล้าง back stack
                    navController.navigate(Screen.ShowAllDocument.route) {
                        popUpTo(Screen.ShowAllDocument.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008000)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp),
                enabled = selectedImageUri != null
            ) {
                Text("บันทึก", color = Color.Black)
            }

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("ยกเลิก", color = Color.Black)
            }
        }

        // **Loading Indicator**
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}