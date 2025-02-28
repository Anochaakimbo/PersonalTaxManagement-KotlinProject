package com.example.myproject.mainscreen

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myproject.viewmodel.DocumentViewModel
import com.example.myproject.api.DocumentItem

@Composable
fun DocumentScreen(navController: NavHostController, viewModel: DocumentViewModel = remember { DocumentViewModel() }) {
    val documentList by viewModel.documents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedYear by remember { mutableStateOf("ทั้งหมด") } // ค่าปีที่เลือก
    val years = listOf("ทั้งหมด","2568", "2567", "2566", "2565", "2564") // รายการปี

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // พื้นหลังสะอาดตา
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
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

            // 🔹 Dropdown เลือกปี
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text(selectedYear)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year) },
                            onClick = {
                                selectedYear = year
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { navController.navigate("savedocument_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("เพิ่มเอกสาร", color = Color.White)
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
                items(documentList.filter { doc ->
                    selectedYear == "ทั้งหมด" || doc.uploaded_at.contains(selectedYear)
                }) { document ->
                    DocumentItemView(navController, document, viewModel)
                }
            }
        }
    }
}

@Composable
fun DocumentItemView(navController: NavHostController, document: DocumentItem, viewModel: DocumentViewModel) {
    var showDialog by remember { mutableStateOf(false) } // ตัวแปรเก็บสถานะของ Dialog
    val imageUrl = "http://10.0.2.2:3000${document.document_url}"
    val formattedDate = viewModel.formatThaiDate(document.uploaded_at)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // ลดความสูงให้พอดีกับเนื้อหา
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (document.document_url.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "รูปภาพของ Document ${document.id}",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "เอกสารที่: ${document.id}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "อัปโหลดเมื่อ: $formattedDate",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { navController.navigate("Seedetaildocument_screen/${document.id}") }
                    ) {
                        Text("ดูรายละเอียด", color = Color(0xFF008000))
                    }

                    TextButton(
                        onClick = { showDialog = true }
                    ) {
                        Text("ลบ", color = Color.Red)
                    }
                }
            }
        }
    }

    // 🔹 **Dialog ยืนยันการลบ**
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("ยืนยันการลบ", fontWeight = FontWeight.Bold) },
            text = { Text("คุณต้องการลบเอกสารนี้หรือไม่?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFile(userId = 1, fileId = document.id)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ลบ", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ยกเลิก", color = Color.Black)
                }
            }
        )
    }
}
