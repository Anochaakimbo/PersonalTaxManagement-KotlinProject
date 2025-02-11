package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myproject.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    LazyColumn( // ใช้ LazyColumn ให้สามารถเลื่อนได้ทั้งหมด
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1FFF3)), // พื้นหลังของทั้งหน้า
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ส่วนหัวโปรไฟล์
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp) // ปรับความสูง
                    .background(Color(0xFF00BFA5)), // สีพื้นหลังส่วนหัว
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.padding(top = 50.dp), // เลื่อนรูปลงมา
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_user),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = 40.dp, y = -30.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_text),
                            contentDescription = "Edit Profile",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "นายดุ่ย ต้นดาตัน",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = "Dui@gmail.com | +01 234 567 89",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 15.sp, letterSpacing = 0.25.sp)
                    )
                }
            }
        }

        // กล่องตั้งค่า
        item {
            ProfileSection(
                title = "ตั้งค่าทั่วไป",
                items = listOf(
                    "แก้ไขข้อมูลส่วนตัว" to R.drawable.id_card,
                    "การแจ้งเตือน" to R.drawable.bell,
                    "เปลี่ยนภาษา" to R.drawable.translate
                )
            )
        }

        item {
            ProfileSection(
                title = "ความปลอดภัย",
                items = listOf(
                    "ความปลอดภัย" to R.drawable.shield,
                    "ธีม" to R.drawable.contrast
                )
            )
        }

        item {
            ProfileSection(
                title = "การช่วยเหลือ",
                items = listOf(
                    "การช่วยเหลือและสนับสนุน" to R.drawable.help,
                    "ติดต่อเรา" to R.drawable.conversation,
                    "ความเป็นส่วนตัว" to R.drawable.unlock
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp)) // ทำให้มีระยะห่างด้านล่าง
        }
    }
}

@Composable
fun ProfileSection(title: String, items: List<Pair<String, Int>>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // ปรับมุมโค้งให้มากขึ้น
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(16.dp))
            .shadow(10.dp, RoundedCornerShape(16.dp)) // เงาที่เข้มขึ้น
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            items.forEach { (text, icon) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = text,
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}
