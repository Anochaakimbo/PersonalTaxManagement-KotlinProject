package com.example.myproject.mainscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.myproject.R // ตรวจสอบให้ตรงกับ package ของแอปจริง
import com.example.myproject.components.TopAppBar




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
                .fillMaxSize() // ทำให้ Scaffold ขยายเต็มที่
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color(0xFFEAFBF1))
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            TaxSavingProducts(navController = navController) // สร้าง component สำหรับแสดง product options
            Spacer(modifier = Modifier.height(16.dp))
            RecommendationsSection() // แนะนำผลิตภัณฑ์
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
fun TaxSavingProducts(navController: NavHostController) {
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
                navController.navigate("lifeInsurance")
            }
            TaxProductItem(R.drawable.ic_pension, "ประกันบำนาญ") {
                navController.navigate("pensionInsurance")
            }
            TaxProductItem(R.drawable.ic_health, "ประกันสุขภาพ") {
                navController.navigate("healthInsurance")
            }
            TaxProductItem(R.drawable.ic_rmf, "กองทุน RMF") {
                navController.navigate("rmfFund")
            }
            TaxProductItem(R.drawable.ic_ssf, "กองทุน SSF") {
                navController.navigate("ssfFund")
            }
        }
    }
}

@Composable
fun TaxProductItem(icon: Int, name: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() } // ทำให้ไอคอนสามารถคลิกได้
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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(text = "แนะนำสำหรับคุณ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        RecommendationItem(R.drawable.ic_insurance, "บีแอลเอ สมาทร์เซฟวิ่ง 10/1", "ผลตอบแทนเฉลี่ย", "2.00% ต่อปี") {
            val url = "https://www.bangkoklife.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
        RecommendationItem(R.drawable.ic_fund_rmf, "K-ESSGI-ThaiESG", "ผลตอบแทนเฉลี่ย", "2.38% ต่อปี") {
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
            .clickable { onClick() }
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

@Composable
fun LifeInsuranceScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite, // ใช้ Icon ที่สื่อถึงชีวิต/ความรัก/ความห่วงใย
            contentDescription = "Life Insurance Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ประกันชีวิตออมทรัพย์", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "เลือกจากแผนประกันที่เหมาะสมกับคุณ", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* คุณสามารถเพิ่ม logic การนำทางดูลายละเอียดเพิ่มได้ */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "ดูรายละเอียดเพิ่มเติม", color = Color.White)
        }
    }
}

@Composable
fun PensionInsuranceScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Home, // ใช้ Icon ที่สื่อถึงบ้าน/ความมั่นคงในวัยเกษียณ
            contentDescription = "Pension Insurance Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ประกันบำนาญ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "วางแผนการเงินเพื่อความมั่นคงในอนาคต", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* คุณสามารถเพิ่ม logic เพื่อดูลายละเอียดเพิ่มได้ */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "ดูรายละเอียดเพิ่มเติม", color = Color.White)
        }
    }
}

@Composable
fun HealthInsuranceScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite, // ใช้ Icon ที่สื่อถึงสุขภาพ/หัวใจ
            contentDescription = "Health Insurance Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ประกันสุขภาพ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "คุ้มครองสุขภาพอย่างสมบูรณ์", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* คุณสามารถเพิ่ม logic การนำทางเพื่อดูลายละเอียดได้ */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "ดูรายละเอียดเพิ่มเติม", color = Color.White)
        }
    }
}

@Composable
fun RMFFundScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.AccountBalance, // ใช้ Icon ที่สื่อถึงการเงิน/บัญชี
            contentDescription = "RMF Fund Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "กองทุน RMF", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "เพิ่มพูนความมั่นคงทางการเงินเพื่อการเกษียณ", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* เพิ่ม logic การนำทางไปยังหน้ารายละเอียดได้ */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "ดูรายละเอียดเพิ่มเติม", color = Color.White)
        }
    }
}

@Composable
fun SSFFundScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart, // ใช้ Icon ที่สื่อถึงการลงทุน/ซื้อของ
            contentDescription = "SSF Fund Icon",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "กองทุน SSF", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ลงทุนเพื่อผลตอบแทนและการลดหย่อนภาษี", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* สามารถเพิ่ม logic การนำทางเพื่อดูลายละเอียดได้ */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "ดูรายละเอียดเพิ่มเติม", color = Color.White)
        }
    }
}


