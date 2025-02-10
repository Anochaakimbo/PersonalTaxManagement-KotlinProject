package com.example.myproject.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myproject.R

@Composable
fun MyApp() {
    val navController = rememberNavController() // ตัวควบคุม Navigation

    NavHost(
        navController = navController,
        startDestination = "options_grid" // หน้าเริ่มต้น
    ) {
        composable("options_grid") { OptionsGrid(navController) }
        composable("income_screen") { IncomeScreen(navController) }
    }
}

@Composable
fun OptionsGrid(navController: NavController) {
    val items = listOf(
        "ประกันสังคม" to R.drawable.account,
        "Easy E-Receipt" to R.drawable.receipt,
        "ดอกเบี้ยบ้าน" to R.drawable.home_24,
        "ประกันชีวิตทั่วไป" to R.drawable.shield,
        "บริจาคทั่วไป" to R.drawable.stars,
        "Thai ESG" to R.drawable.compost
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "เพิ่ม\nค่าลดหย่อนที่มี",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            items.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { (text, icon) ->
                        OptionCard(
                            text = text,
                            iconRes = icon,
                            onClick = {
                                if (text == "ประกันสังคม") {
                                    navController.navigate("income_screen")
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OptionCard(text: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f) // ทำให้เป็นสี่เหลี่ยมจัตุรัส
            .clickable { onClick() }, // เรียกใช้ onClick
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun IncomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header
        Text(
            text = "เงินเดือน และโบนัส",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Main Content
        Text(
            text = "รายได้\n(ก่อนถูกหักภาษี)",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "฿",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(
            onClick = { navController.navigateUp() }, // กลับไปหน้าก่อนหน้า
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("กลับ", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
        }
    }
}
