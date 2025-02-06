package com.example.myproject.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myproject.R
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun HomeScreen() {
    val context = LocalContext.current.applicationContext

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00C39A)), // สีพื้นหลังหลัก
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ไอคอนด้านบน
        Spacer(modifier = Modifier.height(32.dp))
        IconSection()

        // ส่วนเนื้อหาหลักที่มีพื้นหลังมุมโค้ง
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F2F1), shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // พื้นหลังมุมโค้ง
                .padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "แนะนำ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                ContentSection {
                    Toast.makeText(context, "เริ่มคำนวณภาษี", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun IconSection() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        // สามารถเพิ่มไอคอนได้ที่นี่
    }
}

@Composable
fun ContentSection(onItemClick: () -> Unit) {
    Column {
        // การ์ดแนะนำ
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TaxItem(title = "เริ่มคำนวณภาษี", showArrow = true, onItemClick)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Featured", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "ดูทั้งหมด >", color = Color.Blue, fontSize = 14.sp)
        }

        // การ์ด Featured
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
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
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AttachMoney,
                contentDescription = "Money Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        if (showArrow) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


