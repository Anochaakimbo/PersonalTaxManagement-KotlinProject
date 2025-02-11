package com.example.myproject.mainscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 250.dp, height = 200.dp)
                        .background(Color(0xFF00695C), shape = RoundedCornerShape(16.dp)), // เพิ่ม shape เพื่อกำหนดความโค้งของขอบ
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
                            modifier = Modifier
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

                ContentSection {
                    Toast.makeText(context, "เลือกเมนู", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
@Composable
fun ContentSection(onItemClick: () -> Unit) {
    LazyColumn {
        item {
            Text(
                text = "แนะนำ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TaxItem(title = "เริ่มคำนวณภาษี", showArrow = true, onItemClick)
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
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
                    }
                )
            }
        }

        items(5) {
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