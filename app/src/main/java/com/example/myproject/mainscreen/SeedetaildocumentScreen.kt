package com.example.savedocument

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myproject.viewmodel.DocumentViewModel

@Composable
fun SeeDocumentScreen(navController: NavHostController, documentId: Int, viewModel: DocumentViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    val documents by viewModel.documents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(documentId) {
        viewModel.fetchDocumentsById(documentId)
    }

    val document = documents.find { it.id == documentId }
    val imageUrl = "http://10.0.2.2:3000${document?.document_url}"
    Log.d("SeeDocumentScreen", "Document URL: ${document?.document_url}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ย้อนกลับ")
            }
            Text(
                text = "ดูเอกสารที่: $documentId",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Loading / Image Display
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "รูปภาพของ Document $documentId",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("ไม่มีรูปภาพ", fontSize = 16.sp, color = Color.DarkGray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 ปุ่มลบ + ปุ่มเสร็จสิ้น
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // 🔥 ปุ่มลบ (แสดง Dialog ก่อนลบ)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("ลบ", color = Color.Black)
            }

            // ✅ ปุ่มเสร็จสิ้น
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("เสร็จสิ้น", color = Color.Black)
            }
        }
    }

    // 🔹 Dialog ยืนยันก่อนลบ
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("ยืนยันการลบ") },
            text = { Text("คุณต้องการลบเอกสารนี้หรือไม่?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFile(userId = 1, fileId = documentId) // ลบไฟล์
                        showDialog = false
                        navController.popBackStack() // กลับไปหน้าก่อน
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("ลบ", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }
}
