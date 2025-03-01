package com.example.myproject.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.myproject.R
import com.example.myproject.components.TopAppBar

@Composable
fun TaxSavingScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(navController = navController, modifier = Modifier.zIndex(1f)) // ✅ ใช้ zIndex(-1f) ให้เหมือน HomeScreen
        },
        containerColor = Color.White // ✅ เปลี่ยนให้เหมือน HomeScreen
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAFBF1)) // ✅ กำหนดสีพื้นหลังตรงนี้แทน
                .padding(paddingValues), // ✅ ใช้ paddingValues จาก Scaffold
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // ✅ ส่วนของผลิตภัณฑ์ลดหย่อนภาษี
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "ผลิตภัณฑ์ลดหย่อนภาษี",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_savings),
                                contentDescription = "ประกันชีวิต\nออมทรัพย์",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "ประกันชีวิต\nออมทรัพย์", fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_pension),
                                contentDescription = "ประกันบำนาญ",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "ประกันบำนาญ", fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_health),
                                contentDescription = "ประกันสุขภาพ",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "ประกันสุขภาพ", fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_rmf),
                                contentDescription = "กองทุน RMF",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "กองทุน RMF", fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_ssf),
                                contentDescription = "กองทุน SSF",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "กองทุน SSF", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ ส่วนของคำแนะนำ
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "แนะนำสำหรับคุณ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_insurance),
                                contentDescription = "บีแอลเอ สมาทร์เซฟวิ่ง 10/1",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = "บีแอลเอ สมาทร์เซฟวิ่ง 10/1", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = "ผลตอบแทนเฉลี่ย", fontSize = 12.sp, color = Color.Gray)
                                Text(text = "2.00% ต่อปี", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_fund_rmf),
                                contentDescription = "K-ESSGI-ThaiESG",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = "K-ESSGI-ThaiESG", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = "ผลตอบแทนเฉลี่ย", fontSize = 12.sp, color = Color.Gray)
                                Text(text = "2.38% ต่อปี", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
