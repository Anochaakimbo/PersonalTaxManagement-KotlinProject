package com.example.myproject.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.R // ตรวจสอบให้ตรงกับ package จริง
import com.example.myproject.components.TopAppBar
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable

@Composable
fun TaxSavingScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(navController = navController)
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize() // สำคัญ: ทำให้ Scaffold ขยายเต็มที่
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFEAFBF1))
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
                    .weight(1f) // ⭐️ ให้ Column ขยายเต็มพื้นที่ที่เหลือ
            ) {
                HeaderSection()
                Spacer(modifier = Modifier.height(16.dp))
                TaxSavingProducts()
                Spacer(modifier = Modifier.height(16.dp))
                RecommendationsSection()
            }
        }
    }
}


@Composable
fun HeaderSection() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3DDC84)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "แนะนำลดหย่อนภาษี",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "เลือกทางถูกต้อง ด้วยตัวเลือกเหล่านี้",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun TaxSavingProducts() {
    val context = LocalContext.current // 👈 ดึง Context ก่อนใช้งาน

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
            TaxProductItem(R.drawable.ic_savings, "ประกันชีวิต\nออมทรัพย์") {
                Toast.makeText(context, "Clicked ประกันชีวิต", Toast.LENGTH_SHORT).show()
            }
            TaxProductItem(R.drawable.ic_pension, "ประกันบำนาญ") {
                Toast.makeText(context, "Clicked ประกันบำนาญ", Toast.LENGTH_SHORT).show()
            }
            TaxProductItem(R.drawable.ic_health, "ประกันสุขภาพ") {
                Toast.makeText(context, "Clicked ประกันสุขภาพ", Toast.LENGTH_SHORT).show()
            }
            TaxProductItem(R.drawable.ic_rmf, "กองทุน RMF") {
                Toast.makeText(context, "Clicked กองทุน RMF", Toast.LENGTH_SHORT).show()
            }
            TaxProductItem(R.drawable.ic_ssf, "กองทุน SSF") {
                Toast.makeText(context, "Clicked กองทุน SSF", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun TaxProductItem(icon: Int, name: String, onClick: () -> Unit) { // ✅ เอา @Composable ออก
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() } // ✅ ใช้งานได้
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = name,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 12.sp)
    }
}

@Composable
fun RecommendationsSection() {
    val context = LocalContext.current // Get Context

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(text = "แนะนำสำหรับคุณ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        RecommendationItem(R.drawable.ic_insurance, "บีแอลเอ สมาทร์เซฟวิ่ง 10/1", "ผลตอบแทนเฉลี่ย", "2.00% ต่อปี") {
            //Open a URL, replace with the correct one
            val url = "https://www.bangkoklife.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
        RecommendationItem(R.drawable.ic_fund_rmf, "K-ESSGI-ThaiESG", "ผลตอบแทนเฉลี่ย", "2.38% ต่อปี") {
            //Open another URL
            val url = "https://www.kasikornasset.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }
}

@Composable
fun RecommendationItem(icon: Int, title: String, subtitle: String, rate: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() } //Make the card clickable
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
                Text(text = rate, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}