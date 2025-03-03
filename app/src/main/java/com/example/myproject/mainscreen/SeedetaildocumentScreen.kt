package com.example.savedocument

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myproject.viewmodel.DocumentViewModel

@Composable
fun SeeDocumentScreen(navController: NavHostController, documentId: Int, viewModel: DocumentViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    val documents by viewModel.documents.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    // Fixed: Use a specific document state instead of relying on filtering from documents list
    val document = remember { mutableStateOf<com.example.myproject.api.DocumentItem?>(null) }

    // Fetch document data when the screen launches
    LaunchedEffect(documentId) {
        Log.d("SeeDocumentScreen", "Fetching document with ID: $documentId")
        viewModel.fetchDocumentsById(documentId)
    }

    // Update the document state when documents change
    LaunchedEffect(documents) {
        document.value = documents.find { it.id == documentId }
        Log.d("SeeDocumentScreen", "Documents updated, found document: ${document.value}")
    }

    // Properly construct the image URL with necessary fixes
    val imageUrl = document.value?.document_url?.let { url ->
        if (!url.startsWith("http")) {
            // Make sure the URL is properly formed
            if (!url.startsWith("/")) {
                "http://10.0.2.2:3000/$url"
            } else {
                "http://10.0.2.2:3000$url"
            }
        } else {
            url
        }
    } ?: ""

    Log.d("SeeDocumentScreen", "Document: ${document.value}")
    Log.d("SeeDocumentScreen", "Image URL: $imageUrl")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFF0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
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

        // Loading / Image Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (imageUrl.isNotEmpty()) {
                // Fixed: Enhanced image loading with better error handling
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                )

                val imageState = painter.state

                when (imageState) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator()
                    }
                    is AsyncImagePainter.State.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "ไม่สามารถโหลดรูปภาพได้",
                                fontSize = 16.sp,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "URL: $imageUrl",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Error: ${(imageState as? AsyncImagePainter.State.Error)?.result?.throwable?.message}",
                                fontSize = 12.sp,
                                color = Color.Red
                            )
                        }
                    }
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = "รูปภาพของ Document $documentId",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    else -> {
                        Text("กำลังเตรียมรูปภาพ...", fontSize = 16.sp, color = Color.DarkGray)
                    }
                }
            } else {
                Text("ไม่พบรูปภาพ", fontSize = 16.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Delete button
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("ลบ", color = Color.Black)
            }

            // Done button
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

    // Confirmation dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("ยืนยันการลบ") },
            text = { Text("คุณต้องการลบเอกสารนี้หรือไม่?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFile(userId = 1, fileId = documentId)
                        showDialog = false
                        navController.popBackStack()
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