package com.example.myproject.screen

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.myproject.MyBottomBar

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current.applicationContext

    Box(modifier = Modifier.fillMaxSize()) {
        // พื้นหลังส่วนล่าง
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .background(Color.White, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
        )

        // การ์ดตรงกลาง
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 250.dp)
                .align(Alignment.Center)
                .background(Color(0xFF00695C), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                Image(
//                    painter = painterResource(id = R.drawable.money_transfer_test),
//                    contentDescription = "Tax Icon",
//                    modifier = Modifier.size(100.dp).padding(bottom = 8.dp),
//                )
                Text(text = "เริ่มคำนวณภาษี", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "เริ่มคำนวณภาษี", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {
                    Text(text = "START", color = Color(0xFF00695C), fontSize = 16.sp)
                }
            }
        }

        // ส่วนพื้นหลังครีมด้านล่าง
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .padding(top = 10.dp)
                .background(Color(0xFFF5F5DC), shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        )

        // Scaffold สำหรับ Bottom Navigation
        Scaffold(bottomBar = { MyBottomBar(navController, context) }, containerColor = Color.Transparent) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}
        }
    }
}

@Composable
fun ContentSection(onItemClick: () -> Unit) {
    Column {
        Text(text = "แนะนำ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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



