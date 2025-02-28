package com.example.myproject.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavHostController
import com.example.myproject.viewmodel.DocumentViewModel
import com.example.myproject.api.DocumentItem

@Composable
fun DocumentScreen(navController: NavHostController, viewModel: DocumentViewModel = remember { DocumentViewModel() }) {
    val documentList by viewModel.documents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ย้อนกลับ")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "เก็บเอกสาร",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000),
                modifier = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.End) {
                OutlinedButton(
                    onClick = { /* เปิดหน้าข้อมูลผู้ใช้ */ },
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("2568")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Person, contentDescription = "โปรไฟล์")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController.navigate("savedocument_screen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("เพิ่มเอกสาร", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (documentList.isEmpty()) {
            Text(
                text = "ไม่มีเอกสาร",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn {
                items(documentList) { document ->
                    DocumentItemView(navController, document)
                }
            }
        }
    }
}

@Composable
fun DocumentItemView(navController: NavHostController, document: DocumentItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "ไฟล์: ${document.document_url}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "อัปโหลดเมื่อ: ${document.uploaded_at}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { navController.navigate("Seedetaildocument_screen/${document.id}") } // ส่ง document.id ไปด้วย
                ) {
                    Text("ดูรายละเอียด", color = Color(0xFF008000))
                }
            }
        }
    }
}