package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // กล่อง "เริ่มคำนวณภาษี"
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 250.dp)
                    .background(Color(0xFF00695C), shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.money_transfer_test),
                        contentDescription = "Tax Icon",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "เริ่มคำนวณภาษี",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Click Start", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "START",
                            color = Color(0xFF00695C),
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // เรียกใช้ ContentSection
            ContentSection {
                Toast.makeText(context, "เลือกเมนู", Toast.LENGTH_SHORT).show()
            }
        }

        // Scaffold สำหรับวาง Bottom Bar
        Scaffold(
            // สมมติว่ามีฟังก์ชัน MyBottomBar(navController, context) อยู่แล้วในโปรเจ็กต์
            bottomBar = { MyBottomBar(navController, context) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // เนื้อหาอื่น ๆ ภายใน Scaffold (ถ้ามี)
            }
        }
    }
}

@Composable
fun ContentSection(onItemClick: () -> Unit) {
    Column {
        Text(
            text = "แนะนำ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TaxItem(title = "เริ่มคำนวณภาษี", showArrow = true, onItemClick)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Featured", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "ดูทั้งหมด >",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Blue,
                modifier = Modifier.clickable {
                    // Handle navigation หรือ Action อื่น ๆ
                }
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TaxItem(title = "วางแผนภาษี", showArrow = false, onItemClick)
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TaxItem(title = "วางแผนภาษี", showArrow = false, onItemClick)
        }
    }
}

@Composable
fun TaxItem(title: String, showArrow: Boolean, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.money_transfer_test),
            contentDescription = "Money Icon",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 12.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (showArrow) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
