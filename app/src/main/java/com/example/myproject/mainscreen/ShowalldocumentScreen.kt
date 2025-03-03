package com.example.myproject.mainscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myproject.viewmodel.DocumentViewModel
import com.example.myproject.database.Document
import com.example.myproject.loginandsignup.SharedPreferencesManager
import com.example.myproject.navigation.Screen
import com.example.myproject.components.TopAppBar

@Composable
fun DocumentScreen(
    navController: NavHostController,
    viewModel: DocumentViewModel = remember { DocumentViewModel() }
) {
    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    val userId = sharedPreferencesManager.userId ?: 0 // Get userId from SharedPreferences
    val selectedYear = remember { mutableStateOf(sharedPreferencesManager.selectedYear) }
    var expanded by remember { mutableStateOf(false) }

    val documentList by viewModel.documents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val years = listOf(2568, 2567, 2566, 2565, 2564)

    // Use userId from SharedPreferencesManager
    LaunchedEffect(selectedYear.value) {
        viewModel.fetchDocuments(userId, selectedYear.value)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navController = navController,
                modifier = Modifier.zIndex(1f)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "เก็บเอกสาร",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF008000),
                    modifier = Modifier.weight(1f)
                )

                Box {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                            .clickable { expanded = !expanded }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${selectedYear.value}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Year")
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year.toString()) },
                                onClick = {
                                    selectedYear.value = year
                                    sharedPreferencesManager.selectedYear = year
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        navController.navigate("${Screen.SaveDocument.route}/${selectedYear.value}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C99D)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("เพิ่มเอกสาร", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
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
                    itemsIndexed(documentList) { index, document ->
                        DocumentItemView(
                            navController = navController,
                            document = document,
                            viewModel = viewModel,
                            userId = userId,
                            displayNumber = index + 1 // Adding 1 to make it 1-based instead of 0-based
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentItemView(
    navController: NavHostController,
    document: Document,
    viewModel: DocumentViewModel,
    userId: Int, // Pass userId to this function
    displayNumber: Int // New parameter for the sequential number
) {
    var showDialog by remember { mutableStateOf(false) }
    val imageUrl = "http://10.0.2.2:3000${document.documentUrl}"
    val formattedDate = viewModel.formatThaiDate(document.uploadedAt)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
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
            if (document.documentUrl.isNotEmpty()) {
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
                    text = "เอกสารที่: $displayNumber", // Changed from document.id to displayNumber
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
                        onClick = {
                            navController.navigate("${Screen.SeeDetailDocument.route}/${document.id}")
                        }
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("ยืนยันการลบ", fontWeight = FontWeight.Bold) },
            text = { Text("คุณต้องการลบเอกสารนี้หรือไม่?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFile(userId = userId, fileId = document.id) // Use the passed userId
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
